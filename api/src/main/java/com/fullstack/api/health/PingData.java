package com.fullstack.api.health;

import java.time.Instant;

public record PingData(String message, boolean cacheConnected, Instant timestamp) {
}
