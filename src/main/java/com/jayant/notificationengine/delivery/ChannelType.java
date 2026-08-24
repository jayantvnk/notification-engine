package com.jayant.notificationengine.delivery;

/**
 * The set of channels a notification can be delivered through.
 * Phase 1: EMAIL, SSE. SMS/PUSH added later once the two-channel
 * abstraction has proven itself.
 */
public enum ChannelType {
    EMAIL,
    SSE
}
