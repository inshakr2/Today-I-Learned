docker run -d \
  --name prometheus \
  -p 9090:9090 \
  -v $(pwd)/prometheus/config:/etc/prometheus \
  -v $(pwd)/prometheus-data:/prometheus \
  prom/prometheus:v3.1.0 \
  --storage.tsdb.path=/prometheus \
  --config.file=/etc/prometheus/prometheus.yml