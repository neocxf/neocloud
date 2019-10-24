#!/bin/bash
cd `dirname $0`
if [ "$1" = "start" ]; then
	./start.sh
elif [ "$1" = "stop" ]; then
	./stop.sh
elif [ "$1" = "debug" ]; then
	./start.sh debug
elif [ "$1" = "restart" ]; then
	./restart.sh
elif [ "$1" = "dump" ]; then
	./dump.sh
else
	echo "ERROR: Please input argument: start or stop or debug or restart or dump"
    exit 1
				fi
			fi
		fi
	fi
fi
