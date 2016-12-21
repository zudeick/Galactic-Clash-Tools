#!/bin/bash
tshark \
-i venet0 \
-2 \
-q \
-x \
-l \
-f "host 173.230.155.34 and port 80" \
-R "ip.addr == 173.230.155.34 && http && http.response" \
|./parse.sh 173.230.155.34
