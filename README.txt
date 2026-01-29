WizardTD – Java Tower Defense Game (Solo Project)

A complete tower defense game fully implemented by myself using Java, OOP design, and a custom game loop.

WizardTD is a fully playable tower defense game where waves of monsters follow a predefined path toward the wizard’s house.
Players strategically place towers that attack enemies using projectiles, upgrades, and range management.

This project was entirely designed and developed by me, including architecture, logic, rendering, configuration, assets integration, and gameplay mechanics.

Features
Map System

Tile-based map loaded from level files (level1.txt–level4.txt)

BFS-based pathfinding using BFSpoint.java

Rendering of terrain, paths, tower zones, and UI elements

Enemies & Waves

Multiple enemy classes (Enemy.java, Monster.java)

Movement logic along path tiles

HP, speed, reward, and attribute handling

Wave progression and increasing difficulty (Wave.java)

Towers

Implemented in Tower.java

Range, damage, cooldown, and upgrade mechanics

Automatic targeting of nearest valid enemies

Multiple tower sprites with attack animations

Projectiles

Fireball projectile class (Fireball.java)

Calculated trajectories

Hit detection & damage application

Removal and cleanup logic

Wizard House (Base)

Base HP and shield logic

Game-over handling

Loss condition when enemies reach the house

Architecture & Design (Built entirely by me)
✔ Custom Game Loop

Implemented inside App.java:

Update → Move enemies

Update → Tower cooldowns

Update → Fireball movement

Update → Collision detection

Render → Draw entire frame (map, enemies, towers, projectiles)

Runs at 60 FPS

✔ Full Object-Oriented Design

Each component is separated into its own class:

App          (Main loop & rendering)
Map          (Tile parsing & drawing)
Wave         (Scheduling monster waves)
Enemy        (Movement & stats)
Monster      (Base class / subtypes)
Tower        (Attack logic & upgrades)
Fireball     (Projectile behaviour)
House        (Base HP & game-over)
BFSpoint     (Pathfinding helper)

✔ Configuration System

config.json manages game parameters

All values dynamically loaded at runtime

My Role – Sole Developer

I independently built the entire game, including:

Core Gameplay

Enemy AI & path movement

Tower logic, targeting, attack intervals

Projectile physics & collision detection

Wave management and scaling difficulty

System Architecture

Designed all class structures and interactions

Implemented the custom update-render game loop

Built the tile-based map parser

Designed health bar, UI layout, and stats panel

Rendering & Assets

Integrated all sprites (enemies, towers, fireballs, map icons)

Implemented animations and real-time rendering

Testing & Debugging

Manual game balancing

FPS stability

Hitbox precision

Level file parsing

Tech Stack

Java 17+

Processing (JavaFX-style rendering)

Gradle build system

JUnit (basic testing)

JSON parsing

How to Run
./gradlew build
./gradlew run

Project Structure
src/main/java/WizardTD/
│ App.java
│ Map.java
│ BFSpoint.java
│ House.java
│ Wave.java
│ Enemy.java
│ Monster.java
│ Tower.java
└ Fireball.java

Summary

This project showcases my ability to independently design and build a complete interactive system from scratch using Java.
It demonstrates strong skills in:

Object-oriented design

Game loop architecture

Rendering & animation

AI movement & collision detection

Data-driven configuration

Software engineering patterns
