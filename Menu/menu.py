import pygame
from cell import Cell
from button import Button
from number_box import NumberBox

win = pygame.display.set_mode((1700, 880))
pygame.init()

GREEN = (80, 120, 80)
RED = (170, 70, 70)
BLACK = (10, 10, 10)

WHITE = (255, 255, 255)
win.fill (WHITE)


cell_width = 14
cell_height = 11
cells_offset = [45, 20, 1]  # x-offset; y-offset; offset btw cells


cells_scheme = ["wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww",
            "wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww",
            "wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww",
            "www                                      i      sssssss   wwwww",
            "www                                      i     ssssssss   wwwww",
            "www                                      i   sssssssssss  wwwww",
            "www                                      i  sssssssssssss wwwww",
            "www                                      i  sssssssssssss wwwww",
            "www                                      i sssssssssssssss wwww",
            "www                                      i sssssssssssssss wwww",
            "www                                      i sssssssssssssss wwww",
            "www                                      i sssssssssssssss wwww",
            "www                                      i sssssssssssssssswwww",
            "www                                      i ssssssssssssssssswww",
            "www                                      i ssssssssssssssssswww",
            "www                                      i ssssssssssssssssswww",
            "www                                      i ssssssssssssssssswww",
            "www                                      i ssssssssssssssssswww",
            "www                                      i ssssssssssssssssswww",
            "www                                      i ssssssssssssssss www",
            "www                        www           i ssssssssssssssss www",
            "www                        www           i ssssssssssssssss www",
            "www                        www           i  sssssssssssssss www",
            "www                                      i  sssssssssssssss www",
            "www                                      i   sssssssssssss  www",
            "www                                      i    ssssssssssss  www",
            "www                                      i     sssssssssss  www",
            "www                                      i       sssssssss  www",
            "www                                      i         sssssss  www",
            "www                                      i         sssssss  www",
            "www                                      i          sss     www",
            "www                                      i                  www",
            "www                                      i                  www",
            "www                                      i                  www",
            "www                                      i                  www",
            "www                                      i                  www",
            "www                                      i                  www",
            "www                                      i                  www",
            "www                                      i                  www",
            "www                                      i                  www",
            "www                www                   i                 wwww",
            "www                www                   i                 wwww",
            "www                www                   i                 wwww",
            "www                wwww                  i                 wwww",
            "www                                      i                 wwww",
            "www                    l                 i                wwwww",
            "www  l         l                         i                wwwww",
            "www                    l                 i                wwwww",
            "www                                      i                wwwww",
            "www                                      i                wwwww",
            "wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww",
            "wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww",
            "wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww",
    ]
cells = []




for i in range(len(cells_scheme[0])):
    cells.append([])
    for j in range(len(cells_scheme)):
        i_reversed = len(cells_scheme[0]) - 1 - i
        symbol = ' ' if cells_scheme[j][i_reversed] == 's' else cells_scheme[j][i_reversed]
        cells[i].append(Cell(cells_offset[0] + (cells_offset[2]+cell_width)*i, cells_offset[1] + (cells_offset[2]+cell_height)*j, cell_width, cell_height, symbol))

#cells_amount = (108, 53)
cells_amount = (len(cells), len(cells[0]))

allie_sequence = []

def set_point (cell_index_x, cell_index_y):
    cells[cell_index_x][cell_index_y].positions.append(len(allie_sequence) + 1)
    allie_sequence.append ([cell_index_y, cell_index_x])    # Reverse order because the main program has the map vertically,
                                                            # and here it is horizontal
    last_cell_coords = allie_sequence[len(allie_sequence) - 1]
    cells[last_cell_coords[1]][last_cell_coords[0]].type = ' '

def set_loading ():
    if len(allie_sequence) > 0:
        last_cell_coords = allie_sequence[len(allie_sequence) - 1]
        cells[last_cell_coords[1]][last_cell_coords[0]].type = 'l'
        allie_sequence[len(allie_sequence) - 1].append ('l')      # load

def set_deploying ():
    if len(allie_sequence) > 0:
        last_cell_coords = allie_sequence[len(allie_sequence) - 1]
        cells[last_cell_coords[1]][last_cell_coords[0]].type = 's'
        allie_sequence[len(allie_sequence) - 1].append ('s')      # shoot out the game piece

def delete_last ():
    if len(allie_sequence) > 0:
        last_cell = allie_sequence.pop()
        cells[last_cell[1]][last_cell[0]].reset()

set_loading_button = Button (245, 800, 255, 40, 'Set a loading place in the last path point', set_loading)
set_deploying_button = Button (530, 800, 285, 40, 'Set a deployment place in the last path point', set_deploying)
deletion_button = Button (840, 800, 175, 40, 'Delete the last path point', delete_last)
finish_button = Button (1045, 800, 100, 40, 'Done', False)

mode = 0    # 0 - nothing happens; 1 - selection of path; 2 - selection of loading points; 3 - selection of shooting points

lit_cells = []

pygame.draw.rect(win, BLACK, (cells_offset[0] + 35, (cells_offset[2] + cell_height) * cells_amount[1] + 25, 250, 80))
font = pygame.font.SysFont('Arial', 20)
text = font.render('Game piece deployment points', True, GREEN)
win.blit(text, (cells_offset[0] + 40, (cells_offset[2] + cell_height) * cells_amount[1] + 30))
text = font.render('Game piece loading points', True, RED)
win.blit(text, (cells_offset[0] + 40, (cells_offset[2] + cell_height) * cells_amount[1] + 60))

font = pygame.font.SysFont('Arial', 13)

team_number_box = NumberBox ('     Team Number', 400, 660, win, underscore_included=True)
speed_box = NumberBox ('Average Speed During', 600, 660, win, title_line2='Autonomous ONLY')
loading_time_box = NumberBox ('Average Loading Time', 800, 660, win)
deployment_time_box = NumberBox ('Average Deployment Time', 1000, 660, win)

done = False
interrupted = False
while not (done or interrupted):
    for event in pygame.event.get():
        # ------------------ Checks if the window was closed -------------------#
        if event.type == pygame.QUIT:
            interrupted = True
        # ------------------------------ Over --------------------------------- #
        #--------------- Checks if one of the cells was clicked ----------------#
        mouse_x = pygame.mouse.get_pos()[0]
        mouse_y = pygame.mouse.get_pos()[1]

        within_x_range = 0 < mouse_x - cells_offset[0] <= (cells_offset[2] + cell_width) * cells_amount[0] - cells_offset[2]
        within_y_range = 0 < mouse_y - cells_offset[1] <= (cells_offset[2] + cell_height) * cells_amount[1] - cells_offset[2]

        # Put down lit cells:
        for i in range (len(lit_cells)):
            lit_cells[i].lit = False

        if within_x_range and within_y_range:
            within_x_range = 0 < mouse_x - cells_offset[0] <= (cells_offset[2] + cell_width) * cells_amount[0] - cells_offset[2]
            within_y_range = 0 < mouse_y - cells_offset[1] <= (cells_offset[2] + cell_height) * cells_amount[1] - cells_offset[2]

            cell_index_x = int((mouse_x - cells_offset[0]) / (cell_width + cells_offset[2]))
            cell_index_y = int((mouse_y - cells_offset[1]) / (cell_height + cells_offset[2]))

            cells[cell_index_x][cell_index_y].lit = True
            if (not cells[cell_index_x][cell_index_y] in lit_cells):
                lit_cells.append(cells[cell_index_x][cell_index_y])

            if event.type == pygame.MOUSEBUTTONDOWN:
                set_point(cell_index_x, cell_index_y)
                print ("New coordinate entered:", str([cell_index_x, cell_index_y]))

        # ------------------------------ Over --------------------------------- #

        # ------ Checks if the set_loading_button was hovered over or clicked -----#
        within_x_range = 0 < mouse_x - set_loading_button.x <= set_loading_button.width
        within_y_range = 0 < mouse_y - set_loading_button.y <= set_loading_button.height

        if within_x_range and within_y_range:
            set_loading_button.display_active_mode(win)
            if event.type == pygame.MOUSEBUTTONDOWN:
                set_loading_button.display_clicked_mode(win)
                set_loading_button.execute_function ()
        else:
            set_loading_button.display_inactive_mode(win)
        # ------------------------------ Over --------------------------------- #

        # --- Checks if the set_deploying_button was hovered over or clicked ---#
        within_x_range = 0 < mouse_x - set_deploying_button.x <= set_deploying_button.width
        within_y_range = 0 < mouse_y - set_deploying_button.y <= set_deploying_button.height

        if within_x_range and within_y_range:
            set_deploying_button.display_active_mode(win)
            if event.type == pygame.MOUSEBUTTONDOWN:
                set_deploying_button.display_clicked_mode(win)
                set_deploying_button.execute_function()
        else:
            set_deploying_button.display_inactive_mode(win)
        # ------------------------------ Over --------------------------------- #

        # --- Checks if the set_deploying_button was hovered over or clicked ---#
        within_x_range = 0 < mouse_x - deletion_button.x <= deletion_button.width
        within_y_range = 0 < mouse_y - deletion_button.y <= deletion_button.height

        if within_x_range and within_y_range:
            deletion_button.display_active_mode(win)
            if event.type == pygame.MOUSEBUTTONDOWN:
                deletion_button.display_clicked_mode(win)
                deletion_button.execute_function()
        else:
            deletion_button.display_inactive_mode(win)
        # ------------------------------ Over --------------------------------- #

        # --- Checks if the set_deploying_button was hovered over or clicked ---#
        within_x_range = 0 < mouse_x - finish_button.x <= finish_button.width
        within_y_range = 0 < mouse_y - finish_button.y <= finish_button.height

        if within_x_range and within_y_range:
            finish_button.display_active_mode(win)
            if event.type == pygame.MOUSEBUTTONDOWN:
                finish_button.display_clicked_mode(win)
                done = True
        else:
            finish_button.display_inactive_mode(win)
        # ------------------------------ Over --------------------------------- #

        team_number_box.display(mouse_x, mouse_y, event)
        speed_box.display(mouse_x, mouse_y, event)
        loading_time_box.display(mouse_x, mouse_y, event)
        deployment_time_box.display(mouse_x, mouse_y, event)

    else:       # This will execute if the loop finished without a break
        for i in range(cells_amount[0]):
            if i % 5 == 0:
                text = font.render(str (i/2), True, (0, 0, 0))
                win.blit(text, (cells[i][j].x - 1, cells_offset[1] - 14))
            for j in range(cells_amount[1]):
                cells[i][j].display(win)
                if i % 5 == 0:
                    text = font.render(str(j/2), True, (0, 0, 0))
                    win.blit(text, (cells_offset[0] - 25, cells[i][j].y - 2))

        pygame.display.update ()


        continue   # This way, if the for-loop finished without a break, we will do everything that needs to be done. If it
                # Finishes by being broken out of, this branch will be ignored, and the break statement will be
            # reached. So, pygame.quit() thus breaks both loops.
        # Otherwise, if pygame.quit() is not activated, the continue will force python to skip the break.
    break

pygame.quit()

if done:
    print(allie_sequence)
    team_number = team_number_box.text
    team_speed = speed_box.text
    team_l_time = loading_time_box.text
    team_d_time = deployment_time_box.text

    file = open('Allies_strategies/' + team_number + '.txt', 'a')

    file.write(team_speed + ' ')
    file.write(team_l_time + ' ')
    file.write(team_d_time + '\n')

    for i in range(len(allie_sequence)):
        curr_coords = allie_sequence[i]
        file.write('(' + str(cells_amount[0] - 1 - curr_coords[1]) + ', ' + str(curr_coords[0]) + ')' +(curr_coords[2] if len(curr_coords) > 2 else '') + '\n')

    file.write ('e')
    file.close()

else:
    print('\u001b[31m!!ALERT!!\nFinished by closing window:\n\tno file created, try again')