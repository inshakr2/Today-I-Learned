docker run -d \
  --name grafana \
  -p 3000:3000 \
  -v $(pwd)/grafana-data:/var/lib/grafana \
  -v $(pwd)/grafana/provisioning/:/etc/grafana/provisioning/ \
  --restart always \
  --link prometheus \
  grafana/grafana:11.5.0