# The Maze of The Waze project

In this project I made our one version for "TheMazeOfWaze" game. the robot should take as many fruit as he can (there isn't limit of seats) so that he will get the largest score in the game.

Int this project I used diercted illustrated graphs by the DGraph,every node_data in the graph is represent a datum-point in Ariel (I can see the point in 'Google Earth' api) .I used 23 scenario as a game level for the user, the user can change them manualy and decide if to use manual or automatic game. In every scenarion there is a diffrent num of robot, and diffrend amount of fruits . Every game is 60 sec.

In the manual game the user will click on the robot and will move her to the next point by his choice by clicking the next point. Int the automatic game I made the efficient game so that the user will get the largest amount of points by Collect fruits in the way.

Her is a picture of choose the Scenario of the game:

![First](https://user-images.githubusercontent.com/57295881/72775177-093a8a80-3c16-11ea-9dbc-f4b1178ed92e.PNG)


GameClient:

AutoGame class:

represent an automatic game. the class allowing an effective automatic game, by move the robots to an edge on the graph with a fruit, in shortest path.

MyGameGui class represent a graphical game. the class allowing to choose a scenario to the game, place the robots and play the game manually or watch an automatic mode game. in addition, the time left to the end of the game. the manually game allow to play the game by mouse press (on a robot, and the on node on the graph).

KML_loger class create a KML file to the automatic game from the class "MyGameGui". the name of the file will be the scenario number of the game, and the file will save in a folder call "KML_games". SimpleGameClient class represents a simple example for using the GameServer API.


The maual game:

Ths manual game is choosen by the user choice, by clicking on the screen. The user is asked to click first on the red car he wants to move and only after click on the point he want the car to go to .

Notice: the user can click only on robot first and then to the next node to move.

Code Information:

Fruit Class - reprasent all the init and constructor to find the palce of the fruit on the edge.
Robot Class - reprasent all the init and constructor for the robot.
MyThreadClock - reprasent the to run with sleep.

