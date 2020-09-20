#include <iostream>

#include <vector>
#include <string>

#include <stdlib.h> 
#include <time.h>

#include <chrono>

using namespace std; 
using namespace std::chrono;

  
vector<float> get_local_table_peptide(string input) 
{ 
    
    vector<float> table_one;

    for (int i=0; i<26; i++) {

        table_one.push_back(0.0);
    }

    for (int i=0; i<input.length(); i++) {

        table_one[input[i] - 'A'] ++;
    } 
    
    return table_one;
}

vector<vector<float>> get_local_table_dipeptide(string input) 
{ 
    
    vector<vector<float>> table_two;

    for (int i=0; i<26; i++) {

        vector<float> temp;

        for (int j=0; j<26; j++) {

            temp.push_back(0.0);
        }

        table_two.push_back(temp);

    } 

    for (int i=0; i<input.length() - 1; i++) {

        table_two[input[i] - 'A'][input[i+1] - 'A'] ++;
    } 
    
    return table_two;
} 
  
vector<vector<vector<float>>> get_local_table_tripeptide(string input) 
{ 
    
    vector<vector<vector<float>>> table_three;

    for (int i=0; i<26; i++) {

        vector<vector<float>> temp_1;

        for (int j=0; j<26; j++) {

            vector<float> temp_2;

            for (int k=0; k<26; k++) {

                temp_2.push_back(0.0);
            }

            temp_1.push_back(temp_2);

        }

        table_three.push_back(temp_1);

    } 

    for (int i=0; i<input.length() - 2; i++) {

        table_three[input[i] - 'A'][input[i+1] - 'A'][input[i+2] - 'A'] ++;
    } 
    
    return table_three;
} 

float master_one[26][4];
float master_two[26][26][4];
float master_three[26][26][26][4];

void update_master_one (vector<float> local_table_one, float length) {

    for (int i=0; i<26; i++) {

        if (local_table_one[i] != 0)
            master_one[i][0] ++;

        else
            master_one[i][1] ++;

        master_one[i][2] += local_table_one[i]/length;

    }

}

void update_master_two (vector<vector<float>> local_table_two, float length) {

    for (int i=0; i<26; i++) {

        for (int j=0; j<26; j++) {

            if(local_table_two[i][j] != 0)
                master_two[i][j][0] ++;

            else
                master_two[i][j][1] ++;

            master_two[i][j][2] += local_table_two[i][j]/(length-1);
        }

    }
}

void update_master_three (vector<vector<vector<float>>> local_table_three, float length) {

    for (int i=0; i<26; i++) {

        for (int j=0; j<26; j++) {

            for (int k=0; k<26; k++) {

                if(local_table_three[i][j][k] != 0)
                    master_three[i][j][k][0] ++;

                else 
                    master_three[i][j][k][1] ++;

                master_three[i][j][k][2] += local_table_three[i][j][k]/(length-2);
            }

        }

    }
}

int main() {

    auto start_timer = high_resolution_clock::now();

	string input_protein;

    long long total_proteins = 10; //set value

	for (int i=0; i < total_proteins; i++) {

        cin >> input_protein;

        float length = input_protein.length();

        // put check on length < 5

        vector<float> peptide_one = get_local_table_peptide(input_protein);
        // vector<vector<float>> peptide_two = get_local_table_dipeptide(input_protein);
        // vector<vector<vector<float>>> peptide_three = get_local_table_tripeptide(input_protein); 

        update_master_one(peptide_one, length);
        // update_master_two(peptide_two, length);
        // update_master_three(peptide_three, length);

	}

    auto stop_timer = high_resolution_clock::now();

    auto duration = duration_cast <microseconds> (stop_timer - start_timer); 

    cout << "Time taken is: " << duration.count() << " microseconds \n";

    cout << "Master file for single - peptides \n";

    for (int i=0; i<26; i++) {

        char c1 ='A' + i;

        string s;

        s += c1;

        cout << s << "\t";
        cout << master_one[i][0] << "\t" << master_one[i][1] << 
        "\t" << (master_one[i][2]*100.0)/total_proteins;
        cout << "\n";
    }


    // cout << "Master file for double - peptides \n";

    // for (int i=0; i<26; i++) {

    //     for (int j=0; j<26; j++) {

    //         char c1 ='A' + i;
    //         char c2 ='A' + j;

    //         string s;

    //         s += c1;
    //         s += c2;

    //         cout << s << "\t";
    //         cout << master_two[i][j][0] << "\t" << master_two[i][j][1] << 
    //         "\t" << (master_two[i][j][2]*100.0/total_proteins); 
    //         cout << "\n";
    //     }

    // }


    // cout << "Master file for three - peptides \n";

    // for (int i=0; i<26; i++) {

    //     for (int j=0; j<26; j++) {

    //         for (int k=0; k<26; k++) {

    //             char c1 ='A' + i;
    //             char c2 ='A' + j;
    //             char c3 ='A' + k;

    //             string s;

    //             s += c1;
    //             s += c2;
    //             s += c3;
                
    //             cout << s << "\t";
    //             cout << master_three[i][j][k][0] << "\t" << master_three[i][k][j][1] << 
    //             "\t" << (master_three[i][j][k][2]*100.0/total_proteins);
    //             cout << "\n"; 
    //         }

    //     }

    // }

    return 0; 
}