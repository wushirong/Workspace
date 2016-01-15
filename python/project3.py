http://www.codeskulptor.org/#user40_KyjKeqWXZ5ZChAk.py
# template for "Stopwatch: The Game"
import simplegui
import math
# define global variables
WIDTH=500
HEIGHT=500
time=0
x=0
y=0
# define helper function format that converts time
# in tenths of seconds into formatted string A:BC.D
def format(t):
    if math.trunc(t/600)==0:
        float=t%10
        sec=(t-float)/10
        if sec<10:
            return "0:0"+str(sec)+"."+str(float)
        else:
            return "0:"+str(sec)+"."+str(float)
    else:
        minute=math.trunc(t/600)
        t=t-minute*600
        float=t%10
        sec=math.trunc(t/10)
        if sec<10:
            return str(minute)+":0"+str(sec)+"."+str(float)
        else:
            return str(minute)+":"+str(sec)+"."+str(float)
    
# define event handlers for buttons; "Start", "Stop", "Reset"
def start_handler():
    timer.start()
    
def stop_handler():
    global y,x
    timer.stop()
    y=y+1
    if time%5==0:
        x=x+1

def reset_handler():
    global time,x,y
    time=0
    x=0
    y=0


# define event handler for timer with 0.1 sec interval
def tick():
    global time
    time=time+1
    print time


# define draw handler
def draw(canvas):
    t=format(time)
    canvas.draw_text(str(t),[WIDTH/2,HEIGHT/2],24,"red")
    canvas.draw_text(str(x)+"/"+str(y),[450,450],14,"green")

    
# create frame
frame=simplegui.create_frame("stopwathch",WIDTH,HEIGHT)
timer=simplegui.create_timer(100,tick)
frame.set_draw_handler(draw)

# register event handlers
button1=frame.add_button("Start",start_handler,100)
button2=frame.add_button("Stop",stop_handler,100)
button3=frame.add_button("Reset",reset_handler,100)
# start frame
timer.start()
frame.start()

# Please remember to review the grading rubric
