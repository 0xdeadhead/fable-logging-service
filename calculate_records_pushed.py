import boto3
import json

def download_s3_objects(bucket_name, aws_access_key_id, aws_secret_access_key, region_name):
    session = boto3.Session(
        aws_access_key_id=aws_access_key_id,
        aws_secret_access_key=aws_secret_access_key,
        region_name=region_name
    )

    s3 = session.client('s3')
    response = s3.list_objects_v2(Bucket=bucket_name)

    if 'Contents' not in response:
        print("No objects found in the bucket.")
        return []

    downloaded_files = []

    for obj in response['Contents']:
        file_name = obj['Key']
        downloaded_files.append(file_name)

    return downloaded_files

def combine_json_files(bucket_name, files, aws_access_key_id, aws_secret_access_key, region_name):
    session = boto3.Session(
        aws_access_key_id=aws_access_key_id,
        aws_secret_access_key=aws_secret_access_key,
        region_name=region_name
    )

    s3 = session.client('s3')
    combined_data = []

    for file in files:
        response = s3.get_object(Bucket=bucket_name, Key=file)
        data = json.loads(response['Body'].read().decode('utf-8'))

        if isinstance(data, list):
            combined_data.extend(data)

    return combined_data

bucket_name = 'deadhead-n00b'
aws_access_key_id = ''
aws_secret_access_key = ''
region_name = 'ap-south-2'

files = download_s3_objects(bucket_name, aws_access_key_id, aws_secret_access_key, region_name)

combined_data = combine_json_files(bucket_name, files, aws_access_key_id, aws_secret_access_key, region_name)

print(f"Total number of elements in all arrays: {len(combined_data)}")

