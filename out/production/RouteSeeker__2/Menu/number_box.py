import pygame
from button import Button

BLACK = (10, 10, 10)
WHITE = (255, 255, 255)


class NumberBox:
    def __init__(self, title, x, y, win, title_line2 = False, underscore_included = False):
        self.x, self.y = x, y
        self.win = win
        self.text = ''

        self.buttons = []
        self.button_width = 45
        self.button_height = 15
        self.button_offset = 3

        def text_append (increment):
            self.text += increment

        for i in range (12 if underscore_included else 11):
            self.buttons.append(Button (self.x + (i % 3) * (self.button_width + self.button_offset), self.y + 65 + (int(i / 3)) * (self.button_height + self.button_offset), self.button_width, self.button_height, str(i+1), text_append))
        self.buttons[9].text = '0'
        if underscore_included:
            self.buttons[10].text = '_'
            self.buttons[11].text = 'del'
        else:
            self.buttons[10].text = 'del'

        if title_line2:
            font = pygame.font.SysFont('Arial', 20)
            text = font.render(title, True, BLACK)
            self.win.blit(text, (self.x + 2, self.y))
            text = font.render(title_line2, True, BLACK)
            self.win.blit(text, (self.x + 2, self.y + 20))
        else:
            font = pygame.font.SysFont('Arial', 20)
            text = font.render(title, True, BLACK)
            self.win.blit (text, (self.x, self.y, 3*self.button_width + 2*self.button_offset + 1, 20))

    def display (self, mouse_x, mouse_y, event):
        pygame.draw.rect(self.win, BLACK, (self.x, self.y + 40, 3*self.button_width + 2*self.button_offset + 1, 20))
        for i in range(len(self.buttons)):
            button = self.buttons[i]
            within_x_range = 0 < mouse_x - button.x <= button.width
            within_y_range = 0 < mouse_y - button.y <= button.height

            if within_x_range and within_y_range:
                button.display_active_mode(self.win)
                if event.type == pygame.MOUSEBUTTONDOWN:
                    button.display_clicked_mode(self.win)
                    if (i < len (self.buttons) - 1):
                        button.execute_function(button.text)
                    elif len(self.text) > 0:
                        self.text = self.text[:len(self.text) - 1]

            else:
                button.display_inactive_mode(self.win)

        font = pygame.font.SysFont('Arial', 15)
        text = font.render(self.text, True, WHITE)
        self.win.blit(text, (self.x + 2, self.y + 40))
