import sys
import stateRecognizer

if sys.version_info[0] >= 3:
    import PySimpleGUI as sg
else:
    import PySimpleGUI27 as sg
import cv2 as cv
from PIL import Image
import io
from sys import exit as exit
from opencv import analyze

"""
Demo program that displays a webcam using OpenCV
"""


def main():
    sg.ChangeLookAndFeel('LightGreen')

    # define the window layout
    layout = [[sg.Text('OpenCV Demo', size=(40, 1), justification='center', font='Helvetica 20')],
              [sg.Text('Your moves', size=(40, 1), justification='left', font='Helvetica 14')],
              [sg.Multiline(size=(30, 5),disabled=True, key='textbox',justification='top'),sg.Image(filename='', key='image')],
              [sg.ReadButton('Exit', size=(10, 1), pad=((200, 0), 3), font='Helvetica 14'),
               sg.RButton('Capture', size=(10, 1), font='Any 14')]]

    # create the window and show it without the plot
    window = sg.Window('Demo Application - OpenCV Integration',
                       location=(800, 400))
    window.Layout(layout).Finalize()

    #Init the stateRecognizer
    recognizer = stateRecognizer.StateRecognizer()

    # ---===--- Event LOOP Read and display frames, operate the GUI --- #
    cap = cv.VideoCapture(0)
    while True:


        button, values = window.read(timeout=0)

        if button == 'Exit' or values is None:
            sys.exit(0)
        elif button == 'Capture':
            answer = sg.popup_yes_no('Confirming state',
                                     'Are you satisfied with the current state recognized?',
                                     keep_on_top=True)
            if (answer=="Yes"):
                print("Du har gemt det her frame")

            elif (answer=="No"):
                print("Vi genstarter genkendelsen")

        # Capture frame-by-frame
        ret, frame = cap.read()

        #Get OpenCV to recognize
        frame = analyze(cap, recognizer)

        # Display the resulting frame
        #cv.imshow('frame', frame)
        gray = cv.cvtColor(frame, cv.COLOR_BGR2RGB)

        # let img be the PIL image
        img = Image.fromarray(gray)  # create PIL image from frame

        bio = io.BytesIO()  # a binary memory resident stream
        img.save(bio, format='PNG')  # save image as png to it
        imgbytes = bio.getvalue()  # this can be used by OpenCV hopefully
        window.FindElement('image').Update(data=imgbytes)


main()
