from collections import deque

def bfs(v):
    q = deque()
    q.append(v)
    visited[v] = 1
    while q:
        node = q.popleft()
        
        if node == K:
            return visited[K] -1
        
        for nextNode in (node+1, node-1, node*2):
            if 0 <= nextNode < MAX and not visited[nextNode]:
                visited[nextNode] = visited[node] + 1
                q.append(nextNode)


N, K = map(int, input().split())
MAX = 100000 + 1
visited = [0] * (MAX)

print(bfs(N))
