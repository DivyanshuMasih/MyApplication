# -*- coding: utf-8 -*-
"""
Created on Thu Feb  6 08:16:05 2020

@author: Divyanshu
"""


import cv2
import numpy as np
import os
#from random import shuffle
from tqdm import tqdm
TRAIN_DIR_ONE = 'C:/Users/Divyanshu/Pictures/Train'
TRAIN_DIR_TWO = 'C:/Users/Divyanshu/Pictures/road'

IMG_SIZE = 50

def create_dataset() :
    training_data = []
    for img in tqdm(os.listdir(TRAIN_DIR_ONE)):
        path = os.path.join(TRAIN_DIR_ONE,img)
        img = cv2.imread(path,cv2.IMREAD_GRAYSCALE)
        img = cv2.resize(img, (IMG_SIZE,IMG_SIZE))
        #img1 = img[:,:,np.newaxis]
        training_data.append([img,0])
    for img in tqdm(os.listdir(TRAIN_DIR_TWO)):
        path = os.path.join(TRAIN_DIR_TWO,img)
        img = cv2.imread(path,cv2.IMREAD_GRAYSCALE)
        img = cv2.resize(img, (IMG_SIZE,IMG_SIZE))
        #img1 = img[:,:,np.newaxis]
        training_data.append([img,1])
    return np.array(training_data)
   



dataset = create_dataset()
print(dataset[1])
import random

random.shuffle(dataset)
dataset_train = []
dataset_label = []
for x,y in dataset :
    dataset_train.append(x)
    dataset_label.append(y)
dataset = np.array(dataset_train).reshape(-1,50,50,1)
dataset = dataset / 255.0
labels = dataset_label
print(labels.count(0))



from keras.models import Sequential
from keras.layers import Dense, Conv2D, Flatten,MaxPooling2D,Activation
model = Sequential()
model.add(Conv2D(64, kernel_size=3, activation='relu', input_shape=(IMG_SIZE,IMG_SIZE,1)))
model.add(MaxPooling2D(pool_size=(2,2)))
model.add(Conv2D(64, kernel_size=3, activation='relu'))
model.add(MaxPooling2D(pool_size=(2,2)))
model.add(Flatten())
model.add(Dense(64))
model.add(Activation('relu'))
model.add(Dense(1, activation='sigmoid'))


model.compile(optimizer='adam', loss='binary_crossentropy', metrics=['accuracy'])
#from keras.utils import to_categorical
#dataset = to_categorical(datase
model.summary()

model.fit(dataset,labels,batch_size = 32,validation_split = 0.1,epochs=10)    
 
model.save('my_model.h5')
