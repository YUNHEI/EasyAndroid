package com.chen.baseextend.stepby.oo.base

class BaseOOPanel {


    //已经初始化的
    private val addedSwitches by lazy { mutableListOf<MutableList<BaseSwitch>>() }

    //正向传递开关
    private val andSwitches by lazy { mutableListOf<BaseSwitch>().apply { addedSwitches.add(this) } }
    //反向传递开关
    private val norSwitches by lazy { mutableListOf<BaseSwitch>().apply { addedSwitches.add(this) } }
    //切换开关
    private val toggleSwitches by lazy { mutableListOf<BaseSwitch>().apply { addedSwitches.add(this) } }
    //单正向开
    private val andOpenSwitches by lazy { mutableListOf<BaseSwitch>().apply { addedSwitches.add(this) } }
    //单正向关
    private val andCloseSwitches by lazy { mutableListOf<BaseSwitch>().apply { addedSwitches.add(this) } }
    //单反向开
    private val norOpenSwitches by lazy { mutableListOf<BaseSwitch>().apply { addedSwitches.add(this) } }
    //单反向关
    private val norCloseSwitches by lazy { mutableListOf<BaseSwitch>().apply { addedSwitches.add(this) } }


    fun addAndSwitcher(vararg switches: BaseSwitch) {
        andSwitches.addAll(mutableListOf(*switches))
    }

    fun addToggleSwitcher(vararg switches: BaseSwitch) {
        toggleSwitches.addAll(mutableListOf(*switches))
    }

    fun addNorSwitcher(vararg switches: BaseSwitch){
        norSwitches.addAll(mutableListOf(*switches))
    }

    fun addAndOpenSwitcher(vararg switches: BaseSwitch){
        andOpenSwitches.addAll(mutableListOf(*switches))
    }

    fun addAndCloseSwitcher(vararg switches: BaseSwitch) {
        andCloseSwitches.addAll(mutableListOf(*switches))
    }

    fun addNorOpenSwitcher(vararg switches: BaseSwitch){
        norOpenSwitches.addAll(mutableListOf(*switches))
    }

    fun addNorCloseSwitcher(vararg switches: BaseSwitch) {
        norCloseSwitches.addAll(mutableListOf(*switches))
    }

    fun open(o: Any) {

        for (aSwitch in andSwitches) {
            aSwitch.open(o)
        }

        for (norSwitch in norSwitches) {
            norSwitch.close(o)
        }

        for (oSwitch in andOpenSwitches) {
            oSwitch.open(o)
        }

        for (oSwitch in norOpenSwitches) {
            oSwitch.close(o)
        }

        for (oSwitch in toggleSwitches) {
            if (oSwitch.isOpen) {
                oSwitch.close(o)
            } else {
                oSwitch.open(o)
            }
        }
    }

    fun close(o: Any) {

        for (aSwitch in andSwitches) {
            aSwitch.close(o)
        }

        for (norSwitch in norSwitches) {
            norSwitch.open(o)
        }

        for (cSwitch in andCloseSwitches) {
            cSwitch.close(o)
        }

        for (cSwitch in norCloseSwitches) {
            cSwitch.open(o)
        }
    }

    fun clear() {
        addedSwitches.forEach {
            it.clear()
        }
    }
}
