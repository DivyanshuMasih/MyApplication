# MyApplication

First of all, data were collected by the chrome extension image downloader which downloaded from chrome store 
  data  is of bike and car 
  
  1: there was 1094 bikes photos
  2: and 1447 cars
  
  
  than images was converted to gray scale image 
  
  resized 50 * 50
  
  after passing through np.array()
  it was reshaped  as np.array(dataset).reshape(-1,50,50,1)
  
  
  hence dataset was created for CNN which takes input size of 50*50*1
  where 50*50 is image size and it has only one channel
  
  then sequention Convulational neural network was created.
  
  which has following design
  
  1-first layer in convulational layer which has 64 filters and filter or kernel size 3*3
  2-second is relu activation layer 
  3- third layer is maxpooling pool size of 2*2
   
  4- fourth layer in convulational layer which has 64 filters and filter or kernel size 3*3
  5 -fifth is relu activation layer 
  6- sixth layer is maxpooling pool size of 2*2
  7- seventh layer is flatten layer
  8- eigth is dense layer of 64 nodes
  9-than again relu activation is applied
  10-output layer is dense layer sigmoid activation
  
  
  
  this keras model is implemented using keras and tensorflow 
  
  then it is trained on previlsy made dataset
  using fit()
labels are used 0 for car and 1 for bike.

validation split is 0.1 


model is predicting images accurately 


after model was converted to tflite model and used in android app
 
 python file for model creation is also available in repository as car_detection.py which is seperately put.
