package com.chen.baseextend.stepby

import android.os.Handler
import com.chen.baseextend.stepby.entity.*
import com.chen.basemodule.network.base.BaseResponse

/**
 *
 *  Created by chen on 2019/10/17
 **/
object StepBy {

    //同步执行步骤
//    suc  1 执行完成， 0 执行失败继续重试  -1 执行异常不可重试，结束流程
    fun <R, B> arrangeStep(vararg steps: Step<R, B>, request: R? = null, daoBean: B? = null, interruptCallback: ((stepInfo: StepInfo<R, B>, interruptStep: Step<R, B>?, msg: String?, suc: InterruptStatus) -> Unit)? = null, stepName: String = ""): Boolean {

        var req: R? = request

        var bean: B? = daoBean

        var info: StepInfo<R, B>

        val stepPair = findStep(*steps, stepName = stepName, isStep = true, interruptCallback = { intStep, msg, status ->
            interruptCallback?.invoke(StepInfo(req, bean, status), intStep, msg, status)
        })

        stepPair.second?.run {

            if (this is AsyStep) {

                val currentThread = Handler()

                try {
                    asyProcess.invoke(req, {
                        currentThread.post {
                            info = it

                            info.apply {

                                if (nextStepName.isNullOrEmpty()){
                                    for (i in stepPair.first.inc() until steps.size) {
                                        if (this is AsyStep<*,*> || process != null){
                                            nextStepName = i.toString()
                                            return@apply
                                        }
                                    }
                                    nextStepName = stepPair.first.inc().toString()
                                }
                            }

                            if (!info.handlerName.isNullOrEmpty()) {

                                val handlerPair = findStep(*steps, stepName = info.handlerName, isStep = false, interruptCallback = { intStep, msg, status ->
                                    interruptCallback?.invoke(info, intStep, msg, status)
                                })

                                handlerPair.second?.run {
                                    handler?.invoke(info)
                                } ?: return@post
                            }

                            callback?.invoke(BaseResponse(info))

                            if (info.interrupt != null) {
                                interruptCallback?.invoke(info, this, "步骤中断：步骤${stepName}主动中断流程", info.interrupt!!)
                                throw StepException(info.interrupt)
                            }

                            info.apply {
                                req = reqBean

                                val suc = arrangeStep(*steps, request = req, interruptCallback = interruptCallback, stepName = nextStepName!!)
                                if (!suc) throw StepException(info.interrupt)
                            }
                        }
                    })
                } catch (e: StepException) {
                    e.printStackTrace()
                    interruptCallback?.invoke(StepInfo(req, bean, e.interruptStatus), this, "步骤异常：步骤 $stepName 执行异常", e.interruptStatus
                            ?: InterruptStatus.ERROR_TO_NEXT)
                    return@arrangeStep false
                } catch (e: Exception) {
                    e.printStackTrace()
                    interruptCallback?.invoke(StepInfo(req, bean, InterruptStatus.ERROR_TO_NEXT), this, "步骤异常：步骤 $stepName 执行异常", InterruptStatus.ERROR_TO_NEXT)
                    return@arrangeStep false
                }

            } else {

                try {
                    info = process!!.invoke(req to bean)
                } catch (e: Exception) {
                    e.printStackTrace()
                    interruptCallback?.invoke(StepInfo(req, bean, InterruptStatus.ERROR_TO_NEXT), this, "步骤异常：步骤 $stepName 执行异常", InterruptStatus.ERROR_TO_NEXT)
                    return@arrangeStep true
                }

                info.apply {
                    if (nextStepName.isNullOrEmpty()){
                        for (i in stepPair.first.inc() until steps.size) {
                            if (steps[i] is AsyStep<*,*> || steps[i].process != null){
                                nextStepName = i.toString()
                                return@apply
                            }
                        }
                        nextStepName = stepPair.first.inc().toString()
                    }
                }

                if (!info.handlerName.isNullOrEmpty()) {
                    val handlerPair = findStep(*steps, stepName = info.handlerName, isStep = false, interruptCallback = { intStep, msg, status ->
                        interruptCallback?.invoke(info, intStep, msg, status)
                    })

                    handlerPair.second?.run {
                        handler?.invoke(info)
                    } ?: return@arrangeStep true
                }

                callback?.invoke(BaseResponse(info))

                if (info.interrupt != null) {
                    interruptCallback?.invoke(info, this, "步骤中断：步骤${stepName}主动中断流程", info.interrupt!!)
                    if (info.interrupt == InterruptStatus.ERROR_TO_FINISH) return@arrangeStep false
                    return@arrangeStep true
                }

                info.apply {
                    req = reqBean
                    val suc = arrangeStep(*steps, request = req, daoBean = bean, interruptCallback = interruptCallback, stepName = nextStepName!!)
                    if (!suc) return@arrangeStep false
                }
            }
        }
        return false
    }

    private fun <R, B> findStep(vararg steps: Step<R, B>, stepName: String? = null, isStep: Boolean = true, interruptCallback: ((interruptStep: Step<R, B>?, msg: String?, suc: InterruptStatus) -> Unit)? = null): Pair<Int, Step<R, B>?> {

        if (steps.isNullOrEmpty()) {
            interruptCallback?.invoke(null, "步骤中断：steps is null or empty", InterruptStatus.ERROR_TO_FINISH)
            return -1 to null
        }

        if (stepName.isNullOrEmpty()) {
            if (isStep) {
                steps.forEachIndexed { index, step ->
                    if (step.process != null || step is AsyStep) return@findStep index to step
                }
            }
            return -1 to null
        }

        if (stepName == StepName.END) {
            interruptCallback?.invoke(null, "步骤结束，执行完成", InterruptStatus.SUC_TO_FINISH)
            return 200 to null
        }

        steps.forEachIndexed { index, step ->
            if (step.stepName == stepName && (!isStep || isStep && step.process != null)) return@findStep index to step
            if (index.toString() == stepName && (!isStep || isStep && step.process != null)) return@findStep index to step
        }

        interruptCallback?.invoke(null, "步骤中断：找不到步骤名为：${stepName}的步骤", InterruptStatus.ERROR_TO_FINISH)

        return -1 to null
    }
}