import random
import sys


points = []
lines = []
instanceSize = int(input('Instance size: '))
x = [int(x) for x in range(int(input('X size: '))-1)]
y = [int(x) for x in range(int(input('Y size: '))-1)]
i = 1
while len(points) != instanceSize:
    point = [i, random.choice(x), random.choice(y)]
    print(point)
    if point not in points:
        points.append(point)
        i += 1

with open(sys.argv[1], 'w') as file:
    lines.append(f'{instanceSize}\n')
    for point in points:
        lines.append(f'{point[0]} {point[1]} {point[2]}\n')
    file.writelines(lines)
