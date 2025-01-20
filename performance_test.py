import aiohttp
import asyncio
import time
from asyncio import Semaphore

URL = "http://localhost:8080/api/log/"
MAX_REQUESTS_PER_SECOND = 10000
TOTAL_REQUESTS = 10000

semaphore = Semaphore(MAX_REQUESTS_PER_SECOND)

async def send_post_request(i, session):
    async with semaphore:
        json_payload = {"request_id": i, "message": f"Hello from request {i}","fail":True}
        try:
            async with session.post(URL, json=json_payload) as response:
                response_text = await response.text()
                print(f"Request {i}: {response.status} - {response_text[:50]}")
        except Exception as e:
            print(f"Request {i} failed: {e}")

        await asyncio.sleep(1 / MAX_REQUESTS_PER_SECOND)

async def main():
    async with aiohttp.ClientSession() as session:
        tasks = [send_post_request(i, session) for i in range(1, TOTAL_REQUESTS + 1)]
        await asyncio.gather(*tasks)

start_time = time.time()
asyncio.run(main())
end_time = time.time()

print(f"Total time taken: {end_time - start_time:.2f} seconds")

