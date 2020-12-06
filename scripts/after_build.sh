echo "[" > bin/mcmod.info
sed '1d;$d' src/main/resources/mcmod.info >> bin/mcmod.info
echo "," >> bin/mcmod.info
sed '1d;$d' GollumCoreLib/src/main/resources/mcmod.info >> bin/mcmod.info
echo "]" >> bin/mcmod.info
