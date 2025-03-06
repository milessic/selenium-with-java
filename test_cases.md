# Invoice can be generated and file name is proper
__version 1__
1. Open page
2. Fill all mandatory fields
3. Fill item details
4. Click 'Generuj Fakturę'
5. Wait until invoice is downloaded
5. Verify that 'Faktura '{file_name}' została pobrana' message is visible
6. Verify that file name follows structure 'Faktura_{invoiceNumber}.pdf'

# Invoice cannot be generated if there are no invoice items
__version 1__
1. Open page
2. Fill all mandatory fields
3. Remove auto-generated item
4. Click 'Generuj Fakturę'
5. Verify that 'Najpierw dodaj pozycje!' message is visible

# JSON of filled form can be downloaded and it's data is proper
__version 1__
1. Open page
2. Fill all mandatory fields
3. Fill item details
4. Click 'Pobierz plik JSON'
5. Verify that downloaded file name is 'download.json'
6. Open file and verify it has proper structure and all fields are exported

# JSON of filled form can be downloaded and it's data is proper
__version 1__
0. Prepare JSON file for import
1. Open page
2. Click 'Wgraj plik JSON'
3. Select prepared JSON file
4. Verify that all fields are populated
4. Verify that all items are populatd

