import cv2
import numpy as np
from matplotlib import pyplot as plt


# 
# modify this to take another image
#
img = cv2.imread('water_coins.jpg')
#
#


gray = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)


cv2.threshold(gray,0,255,cv2.THRESH_BINARY_INV+cv2.THRESH_OTSU)
ret, thresh = cv2.threshold(gray,0,255,cv2.THRESH_BINARY_INV+cv2.THRESH_OTSU)

# noise removal
kernel = np.ones((3,3),np.uint8)
opening = cv2.morphologyEx(thresh,cv2.MORPH_OPEN,kernel, iterations = 2)

# sure background area
sure_bg = cv2.dilate(opening,kernel,iterations=3)


# Finding sure foreground area
dist_transform = cv2.distanceTransform(opening,cv2.cv.CV_DIST_L2,5)
ret, sure_fg = cv2.threshold(dist_transform,0.7*dist_transform.max(),255,0)

# Finding unknown region
sure_fg = np.uint8(sure_fg)



# plt.imshow(sure_fg, cmap='gray', interpolation='bicubic')
# plt.xticks([]), plt.yticks([]) # to hide tick values on X and Y axis
# plt.show()

contours, hierarchy = \
    cv2.findContours(sure_fg,cv2.RETR_TREE,cv2.CHAIN_APPROX_SIMPLE)

print len(contours)





# plt.imshow(img, cmap='gray', interpolation='bicubic')
# plt.xticks([]), plt.yticks([]) # to hide tick values on X and Y axis
# plt.show()