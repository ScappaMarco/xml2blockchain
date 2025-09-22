import pandas as pd
import statistics
import subprocess
import re
import matplotlib.pyplot as plt

def run_java_benchmark(contact_name, runs):
    #print("IN THE METHOD")
    """
    Esegue il benchmark per un contratto specifico
    :param contact_name:
    :return:
    """
    try:
        cmd = [
            "mvn", "-q", "compile", "exec:java", f"-DcontractName={contact_name}"
        ]

        parsing_times = []
        serializing_times = []
        bc_saving_times = []
        general_times = []
        deserialization_times = []

        #print("AT THE START OF THE FOR LOOP")
        for i in range(runs):
            print("IN THE FOR LOOP")
            print(f"ATTENTION: starting run number {i+1}")
            result = subprocess.run(cmd, capture_output=True, text=True)
            if result.returncode != 0:
                print(f"ERROR - PARSING TIME: errore occurred running {contact_name}: {result.stderr}")
                return None
            else:
                print("RETURN CODE OK")
                for line in result.stdout.splitlines():
                    if "TIME RECORD - PARSING TIME" in line:
                        parsing_time = re.search(r'(\d+)ms', line)
                        print(f"FOUND A PARSING TIME RECORD - {parsing_time}")
                        if parsing_time:
                            parsing_times.append(int(parsing_time.group(1)))
                    elif "TIME RECORD - SERIALIZATION TIME" in line:
                        serialization_time = re.search(r'(\d+)ms', line)
                        print(f"FOUND A SERIALIZATION TIME RECORD - {serialization_time}")
                        if serialization_time:
                            serializing_times.append(int(serialization_time.group(1)))
                    elif "TIME RECORD - BLOCKCHAIN TIME" in line:
                        blockchain_time = re.search(r'(\d+)ms', line)
                        print(f"FOUND A BLOCKCHAIN SAVING TIME RECORD - {blockchain_time}")
                        if blockchain_time:
                            bc_saving_times.append(int(blockchain_time.group(1)))
                    elif "TIME RECORD - GENERAL TIME" in line:
                        general_time = re.search(r'(\d+)ms', line)
                        print(f"FOUND A GENARAL TIME RECORD - {general_time}")
                        if general_time:
                            general_times.append(int(general_time.group(1)))
                    else:
                        if "TIME RECORD - DESERIALIZATION TIME" in line:
                            deserialization_time = re.search(r'(\d+)ms', line)
                            print(f"FOUND A DESERIALIZATION TIME RECORD - {deserialization_time}")
                            if deserialization_time:
                                deserialization_times.append(int(deserialization_time.group(1)))
        means = {
            "contract type": contact_name,
            "parsing mean value": statistics.mean(parsing_times) if parsing_times else None,
            "serialization mean value": statistics.mean(serializing_times) if serializing_times else None,
            "blockchain saving mean time": statistics.mean(bc_saving_times) if bc_saving_times else None,
            "deserialization mean time": statistics.mean(deserialization_times) if deserialization_times else None,
            "general mean time": statistics.mean(general_times) if general_times else None
        }

        return means

    except Exception as e:
        print(f"ERROR - PARSING TIME: exception for contract {contact_name}: {e}")

def main():
    contracts = ["block-contract-dc", "block-contract"]
    #TODO add block-contract to contracts list
    res = []

    for contract in contracts:
        print(f"CONTRACT {contract}: testing")

        results = run_java_benchmark(contract, 5)
        if results:
            print(f"CONTRACT {contract} DONE")
            print(results)
            res.append(results)
    print(res)
    visualize(res)

def visualize(result_dict):
    df = pd.DataFrame(result_dict)
    df = df.set_index("contract type")
    print(df)

    for column in df.columns:
        plt.figure()
        df[column].plot(kind="bar", stacked=True)
        plt.ylabel(f"{column} ms")
        plt.xlabel("contract type")
        plt.xticks(rotation=20)
        plt.tight_layout()
        plt.show()

    for idx, row in df.iterrows():
        plt.figure()
        values = [
            row["parsing mean value"],
            row["serialization mean value"],
            row["deserialization mean time"],
            row["blockchain saving mean time"]
        ]
        labels = ["Parsing", "Serialization", "Blockchain saving", "Deserialization"]

        plt.pie(values, labels=labels, autopct='%1.1f%%', startangle=90)
        plt.title(f"Composition of general time for {idx}")
        plt.show()

if __name__ == "__main__":
    main()