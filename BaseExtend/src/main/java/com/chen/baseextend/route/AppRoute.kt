package com.chen.baseextend.route

/**
 * @author alan
 * @date 2019-06-13
 */
object AppRoute {

    class Main{
        companion object{
            const val PATH_MAIN_FRAGMENT = "/app/main/main_activity"

        }
    }

    class Login {
        companion object {
            const val PATH_LOGIN_ACCOUNT_FRAGMENT = "/app/login/login_account_fragment"
            const val PATH_RE_LOGIN_FRAGMENT = "/app/login/re_login_account_activity"
        }

    }

    class Comm{
        companion object {
            const val PATH_COMM_INFORMATION_FRAGMENT = "/app/comm/comm_information_fragment"

            const val PATH_COMM_APPLY_FRAGMENT = "/app/Comm/comm_apply_fragment"
        }
    }

}