package com.chen.baseextend.repos.viewmodel

import com.chen.baseextend.repos.DownloadRepos

class DownloadModel : MainViewModel(){
    val downloadRepos by lazy { DownloadRepos }
}