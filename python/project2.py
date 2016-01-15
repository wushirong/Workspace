# http://www.codeskulptor.org/#user40_ZOhT7ITIiV00Rms_1.py
#template for "Guess the number" mini-project
# input will come from buttons and an input field
# all output for the game will be printed in the console


import random
import simplegui
import math
secret_number=0
count=0
game_range=0
# helper function to start and restart the game
def new_game(range):
    global secret_number, count,game_range
    game_range=range
    secret_number=random.randrange(0,range)
    count=int(math.ceil(math.log(range,2)))
    print "the guess range is from 0 to "+str(range)+"and you have "+str(count)+" times to try"
     
    


# define event handlers for control panel
def range100():
    new_game(100)
    
    

def range1000():
    new_game(1000)
    
    
def input_guess(guess):
    global count
    count=count-1
    if count<0:
        new_game(game_range)
    guess_num=int(guess)    
    print "Guess was "+guess
    if guess_num>secret_number:
        print "Higher"
    elif guess_num<secret_number:
        print "Lower"
    else:
        print "Correct"

    
# create frame
frame=simplegui.create_frame("Guess the number",500,500)
inp=frame.add_input("put your guess here ",input_guess,100)    
button_1=frame.add_button("range100",range100,100)
button_2=frame.add_button("range1000",range1000,100)

# register event handlers for control elements and start frame


# call new_game 
new_game(100)


# always remember to check your completed program against the grading rubric
