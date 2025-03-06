# Kiwi Chatbot - User Guide

Kiwi is a personal chatbot assistant designed to help you manage tasks such as todos, deadlines, and events. You can
interact with Kiwi using both Command Line Interface (CLI) and a Graphical User Interface (GUI). Kiwi makes it easy to
add, edit, delete, and search tasks to stay organized!

## Table of Contents

- [Quick Start](#quick-start)
- [Features](#features)
    - [Adding a Task](#adding-a-task)
        - [Adding a Todo](#adding-a-todo)
        - [Adding a Deadline](#adding-a-deadline)
        - [Adding an Event](#adding-an-event)
    - [Viewing Tasks](#viewing-tasks)
    - [Marking Tasks as Done](#marking-tasks-as-done)
    - [Unmarking Tasks](#unmarking-tasks)
    - [Deleting Tasks](#deleting-tasks)
    - [Finding Tasks](#finding-tasks)
    - [Editing Tasks](#editing-tasks)
    - [Saving](#saving)
    - [Exiting](#exiting)

## Quick Start

1. Ensure you have `Java 17` installed on your computer.
2. Download the latest release of `kiwi.jar` from GitHub.
3. Open a terminal and navigate to the folder containing `kiwi.jar`.
4. Run the application using the following command:
   `java -jar kiwi.jar`
5. Kiwi will greet you, and you can start entering your tasks.

## Features

### Adding a Task

#### Adding a Todo

Adds a new todo task to the list.

- Format: `todo DESCRIPTION`
- Example:
  `todo Borrow book`

#### Adding a Deadline

Adds a new task with a deadline.

- Format: `deadline DESCRIPTION /by YYYY-MM-DD HH:MM`
- Example:
  `deadline Submit assignment /by 2025-02-28`

#### Adding an Event

Adds a new event task with a start and end time.

- Format: `event DESCRIPTION /from START_TIME /to END_TIME`
- Example:
  `event Project meeting /from 2025-03-01 10:00 /to 2025-03-01 12:00`

### Viewing Tasks

Displays a list of all your tasks.

- Format: `list`

### Marking Tasks as Done

Marks a task as completed.

- Format: `mark INDEX`
- Example:
  `mark 1`

### Unmarking Tasks

Marks a task as incompleted.

- Format: `unmark INDEX`
- Example: `unmark 2`

### Deleting Tasks

Deletes a task from your list.

- Format: `delete INDEX`
- Example:
  `delete 3`

### Finding Tasks

Find tasks by searching for a keyword.

- Format: `find KEYWORD`
- Example:
  `find book`

### Editing Tasks

Edits an existing task based on its index.

- Format: `edit INDEX [/desc NEW_DESCRIPTION] [/by NEW_DEADLINE] [/from NEW_START] [/to NEW_END]`
- Example:
  `edit 2 /desc Complete project proposal`

### Saving

Kiwi automatically saves your tasks to disk after every change. 

### Exiting

Displays the goodbye message and quits the program after 3 seconds.

- Format: `bye`