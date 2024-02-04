# Nand2Tetris Computer

[nand2tetris official site](https://nand2tetris.org)

## Structure
```
nand2tetris/
    |- projects/ ( nand2tetris course files and solutions )
    |- digital/ ( Digital simulator component files )
    |- icons/ ( SVG icons for components )
    |- commit-msg ( git commit hook script )
    |- installgithook.sh ( git commit hook installer )
    |- testgen.awk ( test vector generator for digital ) [ read the file for docs ]
    |- README.md
```

## Tools used
- [Nand2Tetris simulator suite](https://www.nand2tetris.org/software) for course
- [Digital simulator](https://github.com/hneemann/Digital.git) for digital logic design and verification
- [Inkscape](https://inkscape.org) for digital component icons

## Setup
- install the git commit message hook using `./installgithook.sh` to enforce [commit structure](#git)

## Guidelines followed
### projects
- as specefied in the course

### digital
- only use NAND gate from built in library
- use abstracted components instead of using low level components for optimization
- use appropriate custom shape for custom components

### icons
- shape-fill: nil
- shape-alpha: 0
- shape-stroke: black
- shape-stroke-width: 6px
- pin-fontcolor: white
- pin-fontsize: 4pt
- pin-distance: 2 major grid cell
- pin-distance-multibit: 4 major grid cell
#### digital specefic
- digital-pin-input-color: blue
- digital-pin-output-color: red
- digital-pin-label-format: "pin:*[name]*"

### git
- each new creation or fix to existing creation should be commited
- commit message should follow the structure below
    ```
    <type>(scope): description
     |     |       |
     |     |       |-> commit message, can be multiple lines, 
     |     |             but make it short as possible
     |     |
     |     |-> affected part of project, could be either
     |              project - nand2tetris project files
     |              digital - digital simulation files
     |              icons   - digital component icons
     |              docs    - documentations
     |              misc    - anything else
     |
     |-> type of commit, could be either "new" or "fix"
              --[empty line (if body)]--
     [optional body]
    ```
