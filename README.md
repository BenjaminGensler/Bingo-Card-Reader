# Bingo Card Reader

A simple Bingo solver prototype built in Java.

The current version lets you:
- Create a 5x5 bingo card
- Mark called numbers
- Detect Bingo efficiently by checking only the relevant row/column/diagonal after each mark

## Why This Project

This is an early CLI prototype for a future mobile app idea:
- Scan one or more bingo cards with a phone camera
- Track called numbers in real time
- Alert when a card has a winning line

## Current Features

- 5x5 `Card` model
- Each cell stores:
  - `number` (`int`)
  - `marked` (`boolean`)
- Fast bingo detection:
  - after marking a number, only checks possible winning lines for that position
- Command-line input loop for testing

## Project Structure

- `Bingo.java`: main entry point and game loop
- `Card` class (inside `Bingo.java`): card data + marking + bingo checks

## How To Run

From the project root:

```bash
javac Bingo.java
java Bingo