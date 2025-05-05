package com.example.myapplication.viewModel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.myapplication.data.Notification

class NotificationsViewModel : ViewModel() {
    private val _notifications = mutableStateListOf<Notification>()
    val notifications: List<Notification> get() = _notifications

    init {
        _notifications.addAll(
            listOf(
                Notification("Promocodes", "2000tg discount for first order", "Promo"),
                Notification("News", "0.5 fuse tea = 1tg", "News")
            )
        )
    }

    fun addNotification(notification: Notification) {
        _notifications.add(notification)
    }

    fun removeNotification(notification: Notification) {
        _notifications.remove(notification)
    }
}
