title="选择打包的应用"
prompt="选择了:"
options=("gateway" "user" "auth")

echo "$title"
PS3="$prompt "
select opt in "${options[@]}"; do
  if [ "$REPLY" -gt "${#options[@]}" ]; then
      echo "错误的选项"
      continue
  fi
  break
done

cd "asfbbs-$opt" && \
mvn clean package -s ../settings.xml && \
docker build --build-arg PROJECT_NAME=asoul-fan-$opt -t "asoulfan/asoulfan-$opt" -f "../Dockerfile"  . && \
docker push "asoulfan/asoulfan-$opt"