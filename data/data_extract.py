import json
import csv
from watson_developer_cloud import AlchemyLanguageV1

def extract_concepts(qualification_text):
    alchemy_language = AlchemyLanguageV1(api_key='f36f420fb6c6e1e1179c12162b15decfb7e7631a')
    result = alchemy_language.concepts(text=qualification_text)
    concept_list = []
    for concept in result['concepts']:
        concept_list.append(concept['text'])
    return concept_list

with open('input_data.csv', 'r') as oldfile:
    with open('new_data.csv', 'w') as newfile:
        reader = csv.reader(oldfile, delimiter=',')
        writer = csv.writer(newfile, delimiter=',')
        for row in reader:
            text = row[0]
            concept_list = extract_concepts(text)
            writer.writerow(["{}".format(','.join(concept_list)), row[1]])