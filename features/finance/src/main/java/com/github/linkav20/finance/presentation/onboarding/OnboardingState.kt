package com.github.linkav20.finance.presentation.onboarding

data class OnboardingState(
    val step: Step = Step.START,
    val action: Action? = null
) {
    enum class Step(val index: Int) {
        START(0),
        STEP1(1),
        STEP2(2),
        STEP3(3),
        FINISH(4)
    }

    enum class Action { BACK }
}
