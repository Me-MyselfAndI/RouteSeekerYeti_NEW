import pygame

WHITE = (255, 255, 255)
YETI_BLUE = (150, 150, 255)
YETI_BLUE_LIT = (180, 180, 255)
RED_LIT = (200, 50, 50)
RED = (170, 70, 70)
GREEN_LIT = (80, 160, 80)
GREEN = (80, 120, 80)
YELLOW = (100, 100, 50)
BLACK = (10, 10, 10)
PURPLE = (170, 80, 170)
PURPLE_LIT = (200, 100, 200)

class Cell:
    def __init__ (self, x, y, width, height, type = ' '):
        self.x, self.y, self.type = x, y, type
        self.width, self.height = width, height

        self.lit = False
        self.positions = []
        self.DEFAULT_TYPE = self.type

    def display (self, win):
        if self.type == ' ':
            color = YETI_BLUE_LIT if self.lit else YETI_BLUE
        elif self.type == 'w':
            color = BLACK
        elif self.type == 'l':
            color = RED_LIT if self.lit else RED
        elif self.type == 's':
            color = GREEN_LIT if self.lit else GREEN
        elif self.type == 'i':
            color = PURPLE_LIT if self.lit else PURPLE
        else:
            print ("\t!!ALERT!!\nWrong definition of a cell:", self.x, self.y)
            color = YELLOW
        pygame.draw.rect(win, color, (self.x, self.y, self.width, self.height))
        if (len(self.positions) != 0):
            font = pygame.font.SysFont ('Arial', 9)
            text = ''
            for curr_num in self.positions:
                text += str(curr_num) + ','
            text = font.render(text, True, (0, 0, 0))
            win.blit (text, (self.x, self.y))

    def reset (self):
        self.type = self.DEFAULT_TYPE
        self.positions.pop ()
