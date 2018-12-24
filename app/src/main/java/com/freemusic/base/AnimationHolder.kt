package com.freemusic.base

class AnimationHolder {

    var enterAnim: Int = 0
    var exitAnim: Int = 0
    var popEnterAim: Int = 0
    var popExitAim: Int = 0


    constructor(enterAnim: Int, exitAnim: Int, popEnterAim: Int, popExitAim: Int) {
        this.enterAnim = enterAnim
        this.exitAnim = exitAnim
        this.popEnterAim = popEnterAim
        this.popExitAim = popExitAim
    }
}