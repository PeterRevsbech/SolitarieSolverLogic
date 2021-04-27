class StateRecognizer(object):
    def __init__(self):
        self.itemLabels = []
        self.itemCounts = []
        self.x = []
        self.y = []

    def func(self):
        print('hej')

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
