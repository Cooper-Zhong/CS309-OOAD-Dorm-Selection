import requests
import concurrent.futures
import time
# 发送 GET 请求的函数
def send_get_request(url):
    try:
        headers = {
        'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/97.0.4692.71 Safari/537.36',
        'Accept': 'text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8',
        'Accept-Language': 'en-US,en;q=0.5',
        'Accept-Encoding': 'gzip, deflate',
        'Connection': 'keep-alive',
        'Referer': 'http://www.example.com/index.html',
        'Upgrade-Insecure-Requests': '1',
        }
#         user_agent = 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.113 Safari/537.36'
#         headers={'User-Agent':user_agent,}

        start_time = time.time()  # 记录请求开始时间
        response = requests.get(url, headers=headers)  # 发送 GET 请求
        # 在这里可以根据需要处理响应内容或状态码
        end_time = time.time()  # 记录请求结束时间
        response_time = end_time - start_time  # 计算响应时间
        # if response.status_code != 200:
        print(f"Response from {url}: {response.status_code}")
        return response_time  # 返回响应时间
    except requests.RequestException as e:
        print(f"Request failed for {url}: {e}")

# 主函数，用于触发并发请求
def main():
    # 要测试的接口 URL
#     base_url = "http://10.26.80.100:8082"
#     base_url = "http://10.28.8.68:8082"
    base_url = "http://10.32.60.95:8082"
    url1 = base_url+ "/user/findAll"
    url2 = base_url+ "/student/findById/12110517"
    url3 = base_url+ "/room/findOne/16"
    url4 = base_url+ "/room/findAll"
    urls = [url1, url2, url3, url4]    


    # 定义并发的请求数量
    num_requests = 100
    # 存储所有响应时间
    all_response_times = []

    # 创建一个线程池执行并发请求
    with concurrent.futures.ThreadPoolExecutor(max_workers=num_requests) as executor:
        for url in urls:
            # 提交任务到线程池
            future_to_url = {executor.submit(send_get_request, url): url for _ in range(num_requests)}

        # 等待所有任务完成
        for future in concurrent.futures.as_completed(future_to_url):
            url = future_to_url[future]
            try:
                response_time = future.result()  # 获取任务结果
                if response_time is not None:
                    all_response_times.append(response_time)  # 存储响应时间
            except Exception as e:
                print(f"Error occurred for {url}: {e}")

    # 计算平均响应时间
    if all_response_times:
        avg_response_time = sum(all_response_times) / len(all_response_times)
        print(f"Average Response Time: {avg_response_time:.3f} seconds")
    else:
        print("No valid responses recorded")

if __name__ == "__main__":
    main()
