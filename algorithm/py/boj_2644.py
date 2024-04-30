N = int(input())
X, Y = map(int, input().split())
graph = [[0] * (N+1) for i in range(N+1)]
visited = [False] * (N+1)

for i in range(int(input())):
    a, b = map(int, input().split())
    graph[a][b] = graph[b][a] = 1

isCompleted = False

def dfs(v, count):
    print("call dfs " + str(v))
    global isCompleted
    visited[v] = True

    if v == Y:
        isCompleted = True
        print(count)

    for j in graph[v]:
        print(j)
        # print("\n loop")
        # print(i)
        if not visited[j]:
            # print("not visited")
            if j == 1:
                # print("i == 1")
                dfs(j, count+1)

dfs(X, 0)
if not isCompleted:
    print(-1)