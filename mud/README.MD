<h2>MUD</h2>

<code>java -jar mud-1.0.jar <optional_parameter></code>

Run app with optional parameter: path to text file with "dungeon" in its name

<u>If no parameter provided - default/sample Dungeon will load.</u>


<h4>Input file structure:</h4>

[room_id] [n/s/w/e]:[room_id]

example:<br>
r0 n:r3<br>
r1 s:r3 e:r2<br>
r2 s:r5 w:r1<br>
r3 n:r1 s:r0 w:r4<br>
r4 e:r3<br>
r5 n:r2 e:r6<br>
r6 s:r8 e:r7 w:r5<br>
r7 s:r9 w:r6<br>
r8 n:r6 e:r9<br>
r9 n:r7 w:r8<br>