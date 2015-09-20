import cv2
import numpy as np
from matplotlib import pyplot as plt


def water_shed_object_counting(img):
    gray = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)

    ret, thresh = cv2.threshold(gray,220,255,cv2.THRESH_BINARY)

    # noise removal
    kernel = np.ones((5,5),np.uint8)
    opening = cv2.morphologyEx(thresh,cv2.MORPH_OPEN,kernel, iterations = 2)

    # sure background area
    sure_bg = cv2.dilate(opening,kernel,iterations=3)


    # Finding sure foreground area
    dist_transform = cv2.distanceTransform(opening,cv2.cv.CV_DIST_L2,5)
    ret, sure_fg = cv2.threshold(dist_transform,0.7*dist_transform.max(),255,0)

    # Finding unknown region
    sure_fg = np.uint8(sure_fg)

    contours, hierarchy = \
        cv2.findContours(sure_fg,cv2.RETR_TREE,cv2.CHAIN_APPROX_SIMPLE)

    return len(contours)


def count_pills(picture_filename):
    img = cv2.imread(picture_filename)

    # to be tweaked around
    colour_boundaries = {
        'red': ([30, 30, 130], [130, 130, 255]),
        'blue': ([130, 30, 30], [255, 130, 130]),
        'green': ([0, 90, 0], [100, 210, 100])
    }


    pill_counts = {
        'red': 0,
        'blue': 0,
        'green': 0,
    }

    for colour in colour_boundaries:

        # create NumPy arrays from the boundaries
        lower = np.array(colour_boundaries[colour][0], dtype = "uint8")
        upper = np.array(colour_boundaries[colour][1], dtype = "uint8")

     
        # find the colors within the specified boundaries and apply the mask
        colour_mask = cv2.inRange(img, lower, upper)


        # clean the noises
        kernel = np.ones((80,80),np.uint8)
        colour_mask_cleaned = cv2.morphologyEx(colour_mask, cv2.MORPH_CLOSE, kernel)

        colour_region = cv2.bitwise_and(img, img, mask = colour_mask_cleaned)


        pill_counts[colour] = water_shed_object_counting(colour_region)

    return pill_counts

