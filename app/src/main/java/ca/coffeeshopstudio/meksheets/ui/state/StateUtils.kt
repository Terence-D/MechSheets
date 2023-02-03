package ca.coffeeshopstudio.meksheets.ui.state

/**
 * Different type of navigation supported by app depending on size and state.
 */
enum class NavigationType {
    TOP_NAVIGATION, NAVIGATION_RAIL, PERMANENT_NAVIGATION_DRAWER
}

/**
 * Content shown depending on size and state of device.
 */
enum class ContentType {
    LIST_ONLY, LIST_AND_DETAIL
}
