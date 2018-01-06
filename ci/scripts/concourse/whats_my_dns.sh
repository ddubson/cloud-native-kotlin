#!/bin/bash

nslookup google.com | head -1 | awk '{print $2}'