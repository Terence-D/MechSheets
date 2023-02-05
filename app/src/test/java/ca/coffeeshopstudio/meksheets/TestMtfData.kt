package ca.coffeeshopstudio.meksheets

//test data
val testUnitTitle = "TestUnit"
val testModelNumber = "ABC-123"
val testWeight = 20
val testWalk = 7
val testJump = 4

//expected values
val testName = "$testUnitTitle $testModelNumber"

//test input
val testLoad: String = """Version:1.0
$testUnitTitle
$testModelNumber

Mass:$testWeight

Heat Sinks:10 Single
Walk MP:$testWalk
Jump MP:$testJump

Armor:Standard(TECH-BASE)
LA Armor:1
RA Armor:4
LT Armor:3
RT Armor:6
CT Armor:7
HD Armor:8
LL Armor:2
RL Armor:5
RTL Armor:9
RTR Armor:10
RTC Armor:11

Weapons:3
Custom Item 3, Center Torso
Custom Item 1, Left Arm
Custom Item 2, Right Arm

Left Arm:
Shoulder
Upper Arm Actuator
Custom Item 1
-Empty-
-Empty-
-Empty-
-Empty-
-Empty-
-Empty-
-Empty-
-Empty-
-Empty-

Right Arm:
Shoulder
Upper Arm Actuator
Custom Item 2
-Empty-
-Empty-
-Empty-
-Empty-
-Empty-
-Empty-
-Empty-
-Empty-
-Empty-

Left Torso:
Jump Jet
-Empty-
-Empty-
-Empty-
-Empty-
-Empty-
-Empty-
-Empty-
-Empty-
-Empty-
-Empty-
-Empty-

Right Torso:
-Empty-
Jump Jet
-Empty-
-Empty-
-Empty-
-Empty-
-Empty-
-Empty-
-Empty-
-Empty-
-Empty-
-Empty-

Center Torso:
Fusion Engine
Fusion Engine
Fusion Engine
Gyro
Gyro
Gyro
Gyro
Fusion Engine
Fusion Engine
Fusion Engine
Custom Item 3
IS Ammo MG - Full

Head:
Life Support
Sensors
Cockpit
-Empty-
Sensors
Life Support
-Empty-
-Empty-
-Empty-
-Empty-
-Empty-
-Empty-

Left Leg:
Hip
Upper Leg Actuator
Lower Leg Actuator
Foot Actuator
Heat Sink
-Empty-
-Empty-
-Empty-
-Empty-
-Empty-
-Empty-
-Empty-

Right Leg:
Hip
Upper Leg Actuator
Lower Leg Actuator
Foot Actuator
-Empty-
Heat Sink
-Empty-
-Empty-
-Empty-
-Empty-
-Empty-
-Empty-


Overview: The Locust is undoubtedly one of the most popular and prevalent light BattleMechs ever made. First produced in 2499, the almost dozen distinct factories manufacturing the design quickly spread the design to every power in human space. Its combination of tough armor (for its size), exceptional speed, and most importantly, low cost have all contributed to the Locust's success. It remains the benchmark for many scouting designs, and its continual upgrades have ensured that it remains just as effective with every new conflict that appearss.

Capabilities: As the Locust was first developed as a recon platform, speed is paramount to the design's philosophy. While many variants change the weaponry to fill specific tasks or purposes, Locusts are nearly always pressed into service in ways where they can best take advantage of their speed. When in line regiments, they can act as a deadly flankers or harassers, and are often used in reactionary roles to quickly plug holes in a fluid battle line. The structural form of Locusts themselves are their greatest weakness; with no hands, they are disadvantaged in phyisical combat and occasionally have difficulty righting themselves after a fall.

Deployment: One of the most common designs even produced, even the smallest mercenary or pirate outfits will often field one or more of the design. Production for the Locust has continued uninterrupted for centuries, and it plays an important role in the militaries of many smaller nations. The base LCT-1V was once estimated to account for more than 75% of all Locusts in existence at the end of the Succession Wars, though these numbers have dropped with the reappearance of more advanced technology. Still, it remains common in every military worth note.

systemmanufacturer:CHASSIS:Bergan
systemmode:CHASSIS:VII
systemmanufacturer:ENGINE:LTV
systemmode:ENGINE:160
systemmanufacturer:ARMOR:StarSlab
systemmode:ARMOR:/1
systemmanufacturer:COMMUNICATIONS:Garrett
systemmode:COMMUNICATIONS:T10-B
systemmanufacturer:TARGETING:O/P
systemmode:TARGETING:911
"""