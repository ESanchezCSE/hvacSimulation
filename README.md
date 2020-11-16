# HVAC Simulation Android Application

This mobile Android Application tracks a simulated house AC Unit, giving the user real time data about the temperature inside and outside the home.

## Setup

1. A raspberry pi running a python program simulating a house AC Unit in simulated time. 1 Earth day is equivalent to 1.2 minutes in the simulation.
This is done strictly for demonstration purposes. Communication with the real-time database is done through the REST API.

2. A firebase real-time database was used to keep track of the conditions of the house. Both the raspberry pi and android application could read and write
to the database.

3. Android device use to display to the user the real-time data of the house. The user could also control the target temperature of the AC unit in real-time.

## App Demo
![Loading Screen](https://github.com/ESanchezCSE/images/blob/main/afterChangeTemp_hvacSimulation.jpg =420x840)
