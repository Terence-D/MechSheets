package ca.coffeeshopstudio.meksheets

//test input
val testJsonLoad: String = """
{
    "_tons": 20,
    "ammo": [
        {
            "location": "CenterTorso",
            "name": "IS Ammo MG - Full Center Torso",
            "shotsFired": 0
        }
    ],
    "armorCurrent": [
        3,
        7,
        6,
        1,
        4,
        2,
        5,
        8
    ],
    "armorMax": [
        3,
        7,
        6,
        1,
        4,
        2,
        5,
        8
    ],
    "armorRearCurrent": [
        9,
        11,
        10
    ],
    "armorRearMax": [
        9,
        11,
        10
    ],
    "components": {
        "ctComponents": [
            {
                "first": "Fusion Engine",
                "second": true
            },
            {
                "first": "Fusion Engine",
                "second": true
            },
            {
                "first": "Fusion Engine",
                "second": true
            },
            {
                "first": "Gyro",
                "second": true
            },
            {
                "first": "Gyro",
                "second": true
            },
            {
                "first": "Gyro",
                "second": true
            },
            {
                "first": "Gyro",
                "second": true
            },
            {
                "first": "Fusion Engine",
                "second": true
            },
            {
                "first": "Fusion Engine",
                "second": true
            },
            {
                "first": "Fusion Engine",
                "second": true
            },
            {
                "first": "Custom Item 3",
                "second": true
            },
            {
                "first": "IS Ammo MG - Full",
                "second": true
            }
        ],
        "hComponents": [
            {
                "first": "Life Support",
                "second": true
            },
            {
                "first": "Sensors",
                "second": true
            },
            {
                "first": "Cockpit",
                "second": true
            },
            {
                "first": "-Empty-",
                "second": true
            },
            {
                "first": "Sensors",
                "second": true
            },
            {
                "first": "Life Support",
                "second": true
            }
        ],
        "laComponents": [
            {
                "first": "Shoulder",
                "second": true
            },
            {
                "first": "Upper Arm Actuator",
                "second": true
            },
            {
                "first": "Custom Item 1",
                "second": true
            },
            {
                "first": "-Empty-",
                "second": true
            },
            {
                "first": "-Empty-",
                "second": true
            },
            {
                "first": "-Empty-",
                "second": true
            },
            {
                "first": "-Empty-",
                "second": true
            },
            {
                "first": "-Empty-",
                "second": true
            },
            {
                "first": "-Empty-",
                "second": true
            },
            {
                "first": "-Empty-",
                "second": true
            },
            {
                "first": "-Empty-",
                "second": true
            },
            {
                "first": "-Empty-",
                "second": true
            }
        ],
        "llComponents": [
            {
                "first": "Hip",
                "second": true
            },
            {
                "first": "Upper Leg Actuator",
                "second": true
            },
            {
                "first": "Lower Leg Actuator",
                "second": true
            },
            {
                "first": "Foot Actuator",
                "second": true
            },
            {
                "first": "Heat Sink",
                "second": true
            },
            {
                "first": "-Empty-",
                "second": true
            }
        ],
        "ltComponents": [
            {
                "first": "Jump Jet",
                "second": true
            },
            {
                "first": "-Empty-",
                "second": true
            },
            {
                "first": "-Empty-",
                "second": true
            },
            {
                "first": "-Empty-",
                "second": true
            },
            {
                "first": "-Empty-",
                "second": true
            },
            {
                "first": "-Empty-",
                "second": true
            },
            {
                "first": "-Empty-",
                "second": true
            },
            {
                "first": "-Empty-",
                "second": true
            },
            {
                "first": "-Empty-",
                "second": true
            },
            {
                "first": "-Empty-",
                "second": true
            },
            {
                "first": "-Empty-",
                "second": true
            },
            {
                "first": "-Empty-",
                "second": true
            }
        ],
        "raComponents": [
            {
                "first": "Shoulder",
                "second": true
            },
            {
                "first": "Upper Arm Actuator",
                "second": true
            },
            {
                "first": "Custom Item 2",
                "second": true
            },
            {
                "first": "-Empty-",
                "second": true
            },
            {
                "first": "-Empty-",
                "second": true
            },
            {
                "first": "-Empty-",
                "second": true
            },
            {
                "first": "-Empty-",
                "second": true
            },
            {
                "first": "-Empty-",
                "second": true
            },
            {
                "first": "-Empty-",
                "second": true
            },
            {
                "first": "-Empty-",
                "second": true
            },
            {
                "first": "-Empty-",
                "second": true
            },
            {
                "first": "-Empty-",
                "second": true
            }
        ],
        "rlComponents": [
            {
                "first": "Hip",
                "second": true
            },
            {
                "first": "Upper Leg Actuator",
                "second": true
            },
            {
                "first": "Lower Leg Actuator",
                "second": true
            },
            {
                "first": "Foot Actuator",
                "second": true
            },
            {
                "first": "-Empty-",
                "second": true
            },
            {
                "first": "Heat Sink",
                "second": true
            }
        ],
        "rtComponents": [
            {
                "first": "-Empty-",
                "second": true
            },
            {
                "first": "Jump Jet",
                "second": true
            },
            {
                "first": "-Empty-",
                "second": true
            },
            {
                "first": "-Empty-",
                "second": true
            },
            {
                "first": "-Empty-",
                "second": true
            },
            {
                "first": "-Empty-",
                "second": true
            },
            {
                "first": "-Empty-",
                "second": true
            },
            {
                "first": "-Empty-",
                "second": true
            },
            {
                "first": "-Empty-",
                "second": true
            },
            {
                "first": "-Empty-",
                "second": true
            },
            {
                "first": "-Empty-",
                "second": true
            },
            {
                "first": "-Empty-",
                "second": true
            }
        ]
    },
    "description": " ",
    "equipment": [
        {
            "destroyed": false,
            "fired": false,
            "location": "CenterTorso",
            "name": "Custom Item 3"
        },
        {
            "destroyed": false,
            "fired": false,
            "location": "LeftArm",
            "name": "Custom Item 1"
        },
        {
            "destroyed": false,
            "fired": false,
            "location": "RightArm",
            "name": "Custom Item 2"
        }
    ],
    "filename": "TestUnit ABC-1231675038550245.json",
    "heatLevel": 0,
    "heatSinkType": 0,
    "internalCurrent": [
        5,
        6,
        5,
        3,
        3,
        4,
        4,
        3
    ],
    "internalMax": [
        5,
        6,
        5,
        3,
        3,
        4,
        4,
        3
    ],
    "jump": 4,
    "maxHeatSinks": 10,
    "name": "TestUnit ABC-123",
    "pilot": {
        "gunnery": 4,
        "hits": 0,
        "name": "Mechwarrior",
        "piloting": 5
    },
    "walk": 7
}
"""