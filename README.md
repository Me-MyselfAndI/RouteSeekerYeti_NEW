# RouteSeeker_Yeti

This RouteSeeker project is an autonomous generator of autonomous strategy: depending on the actions of our allies, this code 
generates a path that scores as much points for the autonomous as possible. Then, it translates that strategy into the robot code. 
This way, the robot can have as optimal of an autonomous as possible. The path generator is based on machine learning and vector algebra.
The code is adjustable for each new year's game.

Last UPD: 11/18/2020: New GUI added. No map yet

-------

To run the path calculation, use src/Main.java (Run main())
--
To run the menu for path entering, use Menu/menu.py
--
-------

As of 8th of August, 2020, the current version:
 - Creates a path that avoids walls and uses an entered size of robot cell to successfully navigate on the field
 - Needs around 200,000 trials to reliably derive a strategy that scores 20 points in autonomous on a field with no other robots
 - Has a graphic interface to enter allied robot's paths and takes these paths into consideration
 - Has simplified physics that doesn't avoid paths that may make the robot tip over
 - Outputs the strategy into a csv file that stores angles and distances that robot turns/travels from each cell to the next one.

-------

The description of the components of the project:

   Menu.py - a graphic interface to enter robot paths.
   
   RobotPath.csv - sample output of the program
   
   /src:
    
   Cell.java - the field is broken into square sections called cells. Each cell is an instance of Cell class.
    
   Vector.java - a class that works with vectors. This class also contains methods of checking whether the way is clean.
    
   Robot.java - this class is made specifically to describe OUR robot. It includes all kinematic properties and values (such as velocity or current aceleration), the 
sequence of moves, size of the robot cell, amount of cargo and robot's position. This class contains methods that determine where and how long the robot moves.

   SequenceRecord.java - used as a parent class for RobotSequenceRecord and AlliesSequenceRecord (see below)
   
   RobotSequenceRecord.java - used for recording the sequence of positions, accelerations and amounts of cargo robot had during one trial.
   
   AllieSequenceRecords.java - similar class, but for allies. Instead of acceleration, stores velocity.
   
   AlliedRobot.java - interprets and records trajectory that was written into the graphic interface; handles all matters related to drawing allies' paths on the field.
   
   Main.java - does the majority of work. It includes the machine learning part of the code, and also describes the field. The principle by which 
it works is the following:

In each cell there is a so-called value-vector that points in the direction of the most probable high-scoring path. If the robot visited this cell, then the value-vector 
gets mutated: depending on how large was the score at the end of the trial, the vector will be set closer (reward) to or farther (punishment) from 
the direction the robot moved from this cell. The direction of the vector is set to be the major factor that determines where a robot goes from there
at any given trial, but there is some probability to allow for new strategies to appear. This way, after a large number of trials, the program will set the vectors to
point in best possible directions (or at least get to a local maximum of posible scores). The program records every best attempt. In the end, the best of the best attempted paths is given back as an output.


------


      
This project belongs to the FRC Team 3506 Yeti Robotics.

Designed by Grigorii Podoksik
Idea of Caedmon McGinn and Grigorii Podoksik








