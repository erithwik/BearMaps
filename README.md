# BearMaps

I developed the backend for a Berkeley map visualizer and navigation application in Java. This was built using the Apache Maven build system and is deployable to Heroku. The backend utilizes image rasterization to display varying degrees of map detail depending on zoom level and displays optimal routes using the K-D tree data structure with A* graph search algorithm. I was also able to implement an auto-generated related results menu based on user input using a custom-designed trie data structure.

### Data Structures and Algorithms Implemented:
- Priority Queue (with added `change-priority` method)
- K-D Tree
- Trie
- A* 
