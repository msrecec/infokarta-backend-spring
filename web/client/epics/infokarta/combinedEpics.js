import * as deceasedEpics from '../../epics/infokarta/deceased';
import * as fileManagementEpics from '../../epics/infokarta/fileManagement';

export const deceasedAndFileManagementEpic = Object.assign(deceasedEpics, fileManagementEpics);
