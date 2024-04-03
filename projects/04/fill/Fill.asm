// This file is part of www.nand2tetris.org
// and the book "The Elements of Computing Systems"
// by Nisan and Schocken, MIT Press.
// File name: projects/04/Fill.asm

// Runs an infinite loop that listens to the keyboard input.
// When a key is pressed (any key), the program blackens the screen
// by writing 'black' in every pixel;
// the screen should remain fully black as long as the key is pressed. 
// When no key is pressed, the program clears the screen by writing
// 'white' in every pixel;
// the screen should remain fully clear as long as no key is pressed.

@pressed
M=0
@i
M=0
@word
M=0

(LOOP)
    @KBD
    D=M

    @pressed
    D=D-M

    @RESETI
    D;JNE

    @KBD
    D=A

    @i
    D=D-M

    @LOOP
    D;JEQ
    
(CLOOP)
    @KBD
    D=M

    @pressed
    M=D

    @WHITE
    D;JEQ

    @BLACK
    0;JMP

(RESETI)
    @SCREEN
    D=A

    @i
    M=D

    @CLOOP
    0;JMP

(BLACK)
    @i
    D=M

    @word
    A=D
    M=-1

    @INC
    0;JMP

(WHITE)
    @i
    D=M

    @word
    A=D
    M=0

    @INC
    0;JMP

(INC)
    @i
    M=M+1

    @LOOP
    0;JMP
