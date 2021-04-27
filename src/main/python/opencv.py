import cv2
import numpy as np
import time
import stateRecognizer

def analyze(cap, recognizer):

    net = cv2.dnn.readNet("yolov3-tiny.weights", "yolov3-tiny.cfg")  # Tiny Yolo
    with open("coco.names", "r") as f:
        classes = [line.strip() for line in f.readlines()]



    layer_names = net.getLayerNames()
    outputlayers = [layer_names[i[0] - 1] for i in net.getUnconnectedOutLayers()]

    colors = np.random.uniform(0, 255, size=(len(classes), 3))

    font = cv2.FONT_HERSHEY_PLAIN
    starting_time = time.time()
    frame_id = 0


    _, frame = cap.read()  #
    frame_id += 1

    height, width, channels = frame.shape
    # detecting objects
    blob = cv2.dnn.blobFromImage(frame, 0.00392, (320, 320), (0, 0, 0), True, crop=False)  # reduce 416 to 320

    net.setInput(blob)
    outs = net.forward(outputlayers)
    # print(outs[1])

    # Showing info on screen/ get confidence score of algorithm in detecting an object in blob
    class_ids = []
    confidences = []
    boxes = []
    for out in outs:
        for detection in out:
            scores = detection[5:]
            class_id = np.argmax(scores)
            confidence = scores[class_id]
            if confidence > 0.3:
                # onject detected
                center_x = int(detection[0] * width)
                center_y = int(detection[1] * height)
                w = int(detection[2] * width)
                h = int(detection[3] * height)

                # cv2.circle(img,(center_x,center_y),10,(0,255,0),2)
                # rectangle co-ordinaters
                x = int(center_x - w / 2)
                y = int(center_y - h / 2)
                # cv2.rectangle(img,(x,y),(x+w,y+h),(0,255,0),2)

                boxes.append([x, y, w, h])  # put all rectangle areas
                confidences.append(
                    float(confidence))  # how confidence was that object detected and show that percentage
                class_ids.append(class_id)  # name of the object tha was detected

    indexes = cv2.dnn.NMSBoxes(boxes, confidences, 0.4, 0.6)

    for i in range(len(boxes)):
        if i in indexes:
            x, y, w, h = boxes[i]
            label = str(classes[class_ids[i]])
            confidence = confidences[i]
            color = colors[class_ids[i]]
            cv2.rectangle(frame, (x, y), (x + w, y + h), color, 2)
            cv2.putText(frame, label + " " + str(round(confidence, 2)), (x + 100, y + 100), font, 1,
                        (255, 255, 255), 2)
            print(label + ' at (x: '+ str(x+w/2) + ', y: ' + str(y+h/2) + ')')
            recognizer.addItem(label, x+w/2, y+h/2)

    #Overlay 2 - 3 Boxes
    rectColor = [0, 0, 255]
    rectLineWidth=2
    x_pile_witdh=int(width/7)
    y_buffer_top = int(height * 0.30)
    y_buffer_bottom = int(height * 0.35)

    #Stock-Waste
    cv2.rectangle(frame, (0,0),(2*x_pile_witdh,y_buffer_top),rectColor,rectLineWidth) #Upper left pile

    #Foundation
    cv2.rectangle(frame, (3*x_pile_witdh,0),(width,y_buffer_top),rectColor,rectLineWidth) #Upper left pile

    #Tableau
    cv2.rectangle(frame, (0 * x_pile_witdh, y_buffer_bottom), (width, height), rectColor,
                  rectLineWidth)  # Upper left pile



    elapsed_time = time.time() - starting_time
    fps = frame_id / elapsed_time
    cv2.putText(frame, "FPS:" + str(round(fps, 2)), (10, 50), font, 2, (0, 0, 0), 1)

    #cv2.imshow("Image", frame)
    key = cv2.waitKey(1)  # wait 1ms the loop will start again and we will process the next frame
    return frame



""" OVERLAY 1 - 14 BOXES
    #Draw overlay rectangles for piles
    rectColor = [0, 0, 255]
    rectLineWidth=2
    x_pile_witdh=int(width/7)
    y_buffer_top = int(height * 0.30)
    y_buffer_bottom = int(height * 0.35)


    #Upper piles
    cv2.rectangle(frame, (0,0),(x_pile_witdh,y_buffer_top),rectColor,rectLineWidth) #Upper left pile
    cv2.rectangle(frame, (x_pile_witdh,0),(2*x_pile_witdh,y_buffer_top),rectColor,rectLineWidth) #Upper left pile
    #cv2.rectangle(frame, (2*x_pile_witdh,0),(3*x_pile_witdh,y_buffer_top),rectColor,rectLineWidth) #Upper left pile
    cv2.rectangle(frame, (3*x_pile_witdh,0),(4*x_pile_witdh,y_buffer_top),rectColor,rectLineWidth) #Upper left pile
    cv2.rectangle(frame, (4*x_pile_witdh,0),(5*x_pile_witdh,y_buffer_top),rectColor,rectLineWidth) #Upper left pile
    cv2.rectangle(frame, (5*x_pile_witdh,0),(6*x_pile_witdh,y_buffer_top),rectColor,rectLineWidth) #Upper left pile
    cv2.rectangle(frame, (6*x_pile_witdh,0),(7*x_pile_witdh,y_buffer_top),rectColor,rectLineWidth) #Upper left pile

    #Lower piles /tableau piles
    cv2.rectangle(frame, (0 * x_pile_witdh, y_buffer_bottom), (1 * x_pile_witdh, height), rectColor, rectLineWidth)  # Upper left pile
    cv2.rectangle(frame, (1 * x_pile_witdh, y_buffer_bottom), (2 * x_pile_witdh, height), rectColor, rectLineWidth)  # Upper left pile
    cv2.rectangle(frame, (2 * x_pile_witdh, y_buffer_bottom), (3 * x_pile_witdh, height), rectColor, rectLineWidth)  # Upper left pile
    cv2.rectangle(frame, (3 * x_pile_witdh, y_buffer_bottom), (4 * x_pile_witdh, height), rectColor, rectLineWidth)  # Upper left pile
    cv2.rectangle(frame, (4 * x_pile_witdh, y_buffer_bottom), (5 * x_pile_witdh, height), rectColor, rectLineWidth)  # Upper left pile
    cv2.rectangle(frame, (5 * x_pile_witdh, y_buffer_bottom), (6 * x_pile_witdh, height), rectColor, rectLineWidth)  # Upper left pile
    cv2.rectangle(frame, (6 * x_pile_witdh, y_buffer_bottom), (7 * x_pile_witdh, height), rectColor, rectLineWidth)  # Upper left pile
"""