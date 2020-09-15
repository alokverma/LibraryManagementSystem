package com.akki.domain.session

enum class SessionButtonState {
    SCAN_QR_CODE {
        override fun toString(): String {
            return "Scan QRCode"
        }
    },
    END_SESSION {
        override fun toString(): String {
            return "End Session"
        }
    },
    PAY_SESSION {
        override fun toString(): String {
            return "Pay"
        }
    }
}