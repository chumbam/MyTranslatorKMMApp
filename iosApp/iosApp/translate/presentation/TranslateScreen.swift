//
//  TranslateScreen.swift
//  iosApp
//
//  Created by user on 22.01.2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared

struct TranslateScreen: View {
    private var historyDataSourse: HistoryDataSource
    private var translateUseCase: Translate
    @ObservedObject var viewModel: IOSTranslateViewModel
    
    init(historyDataSourse: HistoryDataSource, translateUseCase: Translate) {
        self.historyDataSourse = historyDataSourse
        self.translateUseCase = translateUseCase
        self.viewModel = IOSTranslateViewModel (historyDataSource: historyDataSourse, translateUseCase: translateUseCase)
    }
    
    var body: some View {
        ZStack {
            List {
                HStack(alignment: .center) {
                    LanguageDropDown(
                        language: viewModel.state.fromLanguage,
                        isOpen: viewModel.state.isChoosingFromLanguage,
                        selectLanguage: { language in
                            viewModel.onEvent(event: TranslateEvent.ChooseFromLanguage(language: language))
                        }
                    )
                    Spacer()
                    SwapLanguageButton(onClick: {
                        viewModel.onEvent(event: TranslateEvent.SwapLanguage())
                    })
                    Spacer()
                    LanguageDropDown(
                        language: viewModel.state.toLanguage,
                        isOpen: viewModel.state.isChoosingToLanguage,
                        selectLanguage: { language in
                            viewModel.onEvent(event: TranslateEvent.ChooseToLanguage(language: language))
                    })
                }
                .listRowSeparator(.hidden)
                .listRowBackground(Color.backgroung)
            }
            .listStyle(.plain)
            .buttonStyle(.plain)
        }
        .onAppear{
            viewModel.startObserving()
        }
        .onDisappear{
            viewModel.dispose()
        }
    }
}

