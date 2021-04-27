import math

class StateRecognizer(object):


    def __init__(self,width,height):
        self.width=width
        self.height=height
        self.x_pile_witdh=int(width/7)
        self.y_buffer_top = int(height * 0.30)
        self.y_buffer_bottom = int(height * 0.35)
        self.acceptance_radius = self.x_pile_witdh

        self.reset()

    def reset(self):
        self.itemLabels = []
        self.itemCounts = []
        self.x = []
        self.y = []


    def addItem(self, label, x, y):
        is_contained = False
        for i in range(len(self.itemLabels)):
            if self.itemLabels[i] == label:
                self.itemCounts[i] = self.itemCounts[i] + 1
                self.x[i] = x
                self.y[i] = y
                is_contained = True

        if not is_contained:
            self.itemLabels.append(label)
            self.itemCounts.append(1)
            self.x.append(x)
            self.y.append(y)

    def evaluateFirstRound(self):
        #In the first round - we assume, that it has recognized 7 cards and we want these sorted with regards to x-value
        if len(self.itemLabels) != 7:
            return None
        sorted_x, sorted_labels = zip(*sorted(zip(self.x, self.itemLabels)))
        return sorted_labels


    def evaluate(self, pile, leftPile, rightPile):
        #PIle names: T1, T2, T3,..., T7 + STOCK
        #In the following rounds - we only look at the pile, we want updated
        #Uses leftPile and rightPile to assure, that we are looking at the right pile:
            #E.g. "look for tableau-pile-4 inbetween the pile with KS and 7H"


        if pile == "STOCK":
            #Calcuate expected positon
            x_exp = self.x_pile_witdh
            y_exp = 0

            #Find object closest to expected position
            result = self.closestObject(x_exp,y_exp)
            print(result)



            #Error if closest object is not within acceptance_radius


            #(0,0),(2*x_pile_witdh,y_buffer_top),rectColor,rectLineWidth) #Upper left pile
            self.x_pile_witdh
            self.y_buffer_top

        print()

    def closestObject(self, x_exp, y_exp):
        closest = None
        min_dist = 10000
        for i in range(len(self.itemLabels)):
            x_dist = self.x[i] - x_exp
            y_dist = self.y[i] - y_exp
            dist = math.sqrt(x_dist*x_dist + y_dist*y_dist)
            if (dist < min_dist):
                closest = self.itemLabels[i]
                min_dist = dist
        return closest