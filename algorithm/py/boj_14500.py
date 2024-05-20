def dfs(x, y, node_sum, depth):
    global maxValue

    if depth == 4:
        maxValue = max(maxValue, node_sum)
        return 
    
    for n in range(4):
        mx = x + move_x[n]
        my = y + move_y[n]

        if 0 <= mx < M and 0 <= my < N and not visited[my][mx]:
            
            visited[my][mx] = True
            dfs(mx, my, node_sum + graph[my][mx], depth+1)
            visited[my][mx] = False

def dfs_T(x, y):
    global maxValue
    
    curr_node = graph[y][x]
    nodes = []
    # 입력받은 좌표에서 T 모양 shape 생성 후 max값 갱신
    for n in range(4):
        mx = x + move_x[n]
        my = y + move_y[n]
        if 0 <= mx < M and 0 <= my < N:
            nodes.append(graph[my][mx])
    
    if len(nodes) == 4:
        nodes.remove(min(nodes))
        maxValue = max(maxValue, sum(nodes) + curr_node)
    elif len(nodes) == 3:
        maxValue = max(maxValue, sum(nodes) + curr_node)
    return


N, M = map(int,input().split())
graph = [list(map(int, input().split())) for _ in range(N)]
visited = [[False] * M for _ in range(N)]

move_x = [1, -1, 0, 0]
move_y = [0, 0, 1, -1]
maxValue = 0

for i in range(N):
    for j in range(M):
        visited[i][j] = True
        dfs(j, i, graph[i][j], 1)
        visited[i][j] = False # T shape 을 위해 초기화
        dfs_T(j, i)
        

print(maxValue)