## Lab 1<br>
Task: Create messenger with GUI that uses COM-ports.<br>
I used JavaFX for GUI and JSSC lib for COM-ports interconnection.
You can emulate COM-ports on your PC using utility VSPE (I know VSPE exists for Win, I don't know about Linux)<br>
## Lab 2<br>
Task: Add byte-staffing encryption for messages<br>
The package begin and end are specified with certain byte '\~' (0x7e). So, if such byte is in package, it is 'masked' by another special byte '}' (0x7d). So that byte 0x7d in package should be 'masked' with 0x7d. For example string "I\~love\~{networks}" will be represented in package this way: "\~I}\~love}\~{networks}}\~"
